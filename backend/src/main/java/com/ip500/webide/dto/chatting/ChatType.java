package com.ip500.webide.dto.chatting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatType {
    ENTER("ENTER"),
    EXIT("EXIT"),
    TALK("TALK");

    private final String value;
}
