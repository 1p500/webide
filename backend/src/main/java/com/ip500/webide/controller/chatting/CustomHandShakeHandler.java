package com.ip500.webide.controller.chatting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

/**
 * 웹소켓 핸드쉐이크 과정에서 WebSocketMemberSessionMapper의 키인 sessionId를 STOMP에 저장해서 CONNECTED 시에 사용자가 확인 가능.
 * 이 핸들러는 서버가 클라이언트에 보내주려면 Member 설정을 반드시 해야하기에 필요합니다.
 */

@Slf4j
@Component
public class CustomHandShakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String sessionId = UUID.randomUUID().toString();
        attributes.put("WebSocketMemberSessionId", sessionId);
        return new StompPrincipal(sessionId);
    }
}
