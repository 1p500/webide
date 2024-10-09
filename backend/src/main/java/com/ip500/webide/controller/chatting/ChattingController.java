package com.ip500.webide.controller.chatting;

import com.ip500.webide.dto.chatting.ChattingContentRequestDto;
import com.ip500.webide.dto.chatting.ChattingResponseDto;
import com.ip500.webide.dto.chatting.WebSocketMember;
import com.ip500.webide.dto.chatting.WebSocketMemberSessionMapper;
import com.ip500.webide.service.chatting.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingService chattingService;
    private final SimpMessagingTemplate template;
    private final WebSocketMemberSessionMapper webSocketMemberSessionMapper;

    /**
     * 채팅방 subscribe 시 입장 알림 및 웹 소켓 세션 등록하기
     */
    @SubscribeMapping("/project/{projectId}/chat")
    public void enter(
            SimpMessageHeaderAccessor headerAccessor,
            @DestinationVariable("projectId") Long projectId
    ) {
        // 세션 방식을 이용해서 유저 아이디 가져오기
        String memberName = getMemberName(headerAccessor);
        log.trace("웹소켓 [채팅] [입장] 메시지 출력 projectId = {}, name = {}", projectId, memberName);

        ChattingResponseDto enterMassage = chattingService.enter(projectId, memberName);
        template.convertAndSend("/topic/project/" + projectId + "/chat", enterMassage);
    }

    /**
     * 채팅방 subscribe 시 입장 알림
     */
    @MessageMapping("/project/{projectId}/chat-create")
    @SendTo("/topic/project/{projectId}/chat")
    public ChattingResponseDto talk(
            SimpMessageHeaderAccessor headerAccessor,
            @Payload ChattingContentRequestDto chattingContentRequestDto
    ) {
        // 세션 방식을 이용해서 유저 아이디 가져오기
        String memberName = getMemberName(headerAccessor);
        Long projectId = getProjectId(headerAccessor);

        log.trace("웹소켓 [채팅] [메시지] 출력 chattingContentRequestDto = {}, projectId = {}, memberName = {}", chattingContentRequestDto, projectId, memberName);

        return chattingService.talk(chattingContentRequestDto, memberName, projectId);
    }

    /**
     * 퇴장 메시지 컨트롤러
     */
    public void exit(
            Long projectId,
            String memberName
    ) {
        log.trace("웹소켓 [채팅] [퇴장] 메시지 출력 projectId = {}, memberName = {}", projectId, memberName);
        ChattingResponseDto exitMessage = chattingService.exit(memberName, projectId);
        template.convertAndSend("/topic/project/" + projectId + "/chat", exitMessage);
    }

    /**
     * headerAccessor에서 세션 아이디 추출
     */
    public String getSessionId(SimpMessageHeaderAccessor headerAccessor) {
        ConcurrentHashMap<String, String> simpSessionAttributes = (ConcurrentHashMap<String, String>) headerAccessor.getMessageHeaders().get("simpSessionAttributes");
        if (simpSessionAttributes == null) {
            log.trace("웹소켓 세션 아이디를 찾을 수 없습니다!");
            throw new RuntimeException("웹소켓 세션 아이디를 찾을 수 없습니다.");
        }

        return simpSessionAttributes.get("WebSocketMemberSessionId");
    }

    /**
     * sessionId 이용해 memberName 반환
     */
    private String getMemberName(SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = getSessionId(headerAccessor);

        WebSocketMember webSocketMember = webSocketMemberSessionMapper.get(sessionId);
        return webSocketMember.getMemberInfoDto().getName();
    }

    /**
     * sessionId 이용해 projectId 반환
     */
    private Long getProjectId(SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = getSessionId(headerAccessor);

        WebSocketMember webSocketMember = webSocketMemberSessionMapper.get(sessionId);
        return webSocketMember.getProjectId();
    }
}
