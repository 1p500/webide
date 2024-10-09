package com.ip500.webide.controller.chatting;

import com.ip500.webide.dto.chatting.WebSocketMember;
import com.ip500.webide.dto.chatting.WebSocketMemberSessionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final ChattingController chattingController;
    private final WebSocketMemberSessionMapper webSocketMemberSessionMapper;

    /**
     * Disconnect 시 채팅방 퇴장 알림 기능 구현
     */
    @Transactional
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.trace("웹소켓 [DISCONNECT] 요청");

        // simpSessionAttributes에 존재하는 uuid 가져오기
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        ConcurrentHashMap<String, String> simpSessionAttributes = (ConcurrentHashMap<String, String>) headerAccessor.getMessageHeaders().get("simpSessionAttributes");

        if (simpSessionAttributes == null) {
            log.trace("웹소켓 [ABNORMAL DISCONNECT]");
            throw new RuntimeException("웹소켓 세션 아이디가 존재하지 않습니다.");
        }

        String sessionId = simpSessionAttributes.get("WebSocketMemberSessionId");

        // WebSocketMemberSessionMapper에 해당하는 유저의 sessionId 없애기
        WebSocketMember removeWebSocketMember = webSocketMemberSessionMapper.remove(sessionId);

        // ChattingController exit 메서드 실행
        chattingController.exit(removeWebSocketMember.getProjectId(), removeWebSocketMember.getMemberInfoDto().getName());
    }
}
