package com.ip500.webide.controller.chatting;

import java.security.Principal;

/**
 * 유저정보(WebSocketMemberSessionMapper)의 키 값을 주고 받기 위한 값
 */
public class StompPrincipal implements Principal {

    private String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
