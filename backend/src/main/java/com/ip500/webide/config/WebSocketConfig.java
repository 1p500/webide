package com.ip500.webide.config;

import com.ip500.webide.controller.chatting.CustomHandShakeHandler;
import com.ip500.webide.controller.chatting.WebSocketChannelInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 웹소켓 관련 기본 설정하기
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer { // 소켓 연결을 구성

    private final WebSocketChannelInterceptor webSocketChannelInterceptor;
    private final CustomHandShakeHandler customHandShakeHandler;

    /**
     * 메시지 브로커의 구성 정의
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // 내부 브로커 설정
        registry.setApplicationDestinationPrefixes("/app", "/topic", "/queue"); // 서버 거치는 접두사 설정
        registry.setUserDestinationPrefix("/member"); // 사용자에게 전송하는 접두사
    }

    /**
     * STOMP endPoint 등록
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // POSTMAN TEST 용도 (PR, 배포 시 반드시 주석처리 돼있어야 함.)
        registry.addEndpoint("/ws/ide/test")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(customHandShakeHandler);

        // withSockJS 사용 용도
//        registry.addEndpoint("/ws/ide")
//                .setAllowedOriginPatterns("*")
//                .setHandshakeHandler(customHandShakeHandler)
//                .withSockJS();
    }

    /**
     * 클라이언트가 보낸 STOMP 명령어 올 때 거치는 인터셉터 설정
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketChannelInterceptor);
    }
}
