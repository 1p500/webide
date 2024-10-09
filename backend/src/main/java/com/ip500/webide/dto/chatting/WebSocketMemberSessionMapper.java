package com.ip500.webide.dto.chatting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketMemberSessionMapper {
    private final ConcurrentHashMap<String, WebSocketMember> webSocketMemberSessionMap;

    public WebSocketMemberSessionMapper() {
        this.webSocketMemberSessionMap = new ConcurrentHashMap<>();
    }

    public void put(String uuid, WebSocketMember webSocketMember) {
        this.webSocketMemberSessionMap.put(uuid, webSocketMember);
    }

    public WebSocketMember get(String uuid) {
        return this.webSocketMemberSessionMap.get(uuid);
    }

    public WebSocketMember remove(String uuid) {
        WebSocketMember webSocketMember = this.webSocketMemberSessionMap.remove(uuid);
        if (webSocketMember == null) {
            throw new RuntimeException("웹소켓 DISCONNECT 시 유저정보가 없어서 띄우는 에러");
        }
        return webSocketMember;
    }

    /**
     * 테스트용 클리어 코드
     */
    public void clear() {
        webSocketMemberSessionMap.clear();
    }
}
