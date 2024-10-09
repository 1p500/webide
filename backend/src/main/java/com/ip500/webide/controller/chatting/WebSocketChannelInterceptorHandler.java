package com.ip500.webide.controller.chatting;

import com.ip500.webide.domain.user.Member;
import com.ip500.webide.dto.chatting.MemberInfoDto;
import com.ip500.webide.dto.chatting.WebSocketMember;
import com.ip500.webide.dto.chatting.WebSocketMemberSessionMapper;
import com.ip500.webide.jwt.JWTUtil;
import com.ip500.webide.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChannelInterceptorHandler {

    private final WebSocketMemberSessionMapper webSocketMemberSessionMapper;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    /**
     * STOMP CONNECT 인터셉터 핸들러 수행
     */
    public void stompConnect(
            String jwtToken,
            Long projectId,
            String sessionId
    ) {
        String token = jwtToken.split(" ")[1];
        String userLoginId = jwtUtil.getLoginId(token);
        Member member = memberRepository.findByUserId(userLoginId);
        MemberInfoDto memberInfoDto = new MemberInfoDto(member);
        log.trace("웹소켓 사용자 ID, 이름 가져오기 : {} {}", memberInfoDto.getId(), memberInfoDto.getName());

        // 세션 등록
        webSocketMemberSessionMapper.put(sessionId, new WebSocketMember(memberInfoDto, projectId));
    }

    /**
     * STOMP SUBSCRIBE 인터셉터 핸들러 수행
     */
    public void stompSubscribe(
            WebSocketMember webSocketMember,
            Long subscribeProjectId,
            String subscribeType
    ) {
        log.trace("웹소켓 [SUBSCRIBE] 요청");
        // 클라이언트가 보낸 STOMP 메시지의 사용자가 유효한 사용자인지 체크

        checkEqualsSubscribeDestinationByProjectId(webSocketMember, subscribeProjectId);
        checkExistsBySubscribeType(webSocketMember, subscribeType);

        // 구독 안했으면 구독해주기
        webSocketMember.startSubscribe(subscribeType);
    }

    /**
     * STOMP SEND 인터셉터 핸들러 수행
     */
    public void stompSend(
            WebSocketMember webSocketMember,
            Long subscribeProjectId,
            String subscribeType
    ) {
        log.trace("웹소켓 [SUBSCRIBE] 요청");
        // 클라이언트가 보낸 STOMP 메시지의 사용자가 유효한 사용자인지 체크

        // Destination 주소 검증
        checkEqualsSubscribeDestinationByProjectId(webSocketMember, subscribeProjectId);
        checkExistsBySubscribeType(webSocketMember, subscribeType);
    }

    /**
     * STOMP SUBSCRIBE 시 동일한 구독을 방지하는 로직
     */
    private void checkExistsBySubscribeType(
            WebSocketMember webSocketMember,
            String subscribeType
    ) {
        log.trace("웹소켓 [SubscribeType] = {}", subscribeType);

        // 이미 구독한 상태일 때
        if (webSocketMember.isSubscribe(subscribeType)) {
            log.warn("웹소켓 구독 중복 에러입니다. subscribeType = {}", subscribeType);
            throw new RuntimeException("이미 구독한 채널입니다.");
        }
    }

    /**
     * STOMP SUBSCRIBE 시 다른 projectId의 구독을 방지하는 로직
     */
    private void checkEqualsSubscribeDestinationByProjectId(
            WebSocketMember webSocketMember,
            Long subscribeProjectId
    ) {
        // Destination 주소 검증
        log.trace("웹소켓 Destination 주소가 같은지 판별");

        // 사용자가 구독한 projectId 반환
        Long projectId = webSocketMember.getProjectId();
        log.trace("웹소켓 Destination 주소 : [현재 주소] projectId = {}, [구독 주소] subscribeProjectId = {}", projectId, subscribeProjectId);

        // subscribeProjectId와 projectId를 비교하여 같은 경우 구독번호를 반환한다.
        if (!subscribeProjectId.equals(projectId)) {
            log.warn("웹소켓 접속한 프로젝트와 구독하고자 하는 프로젝트가 다릅니다.");
            throw new RuntimeException("해당 프로젝트로 접근할 수 없습니다.");
        }
    }
}
