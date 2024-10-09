package com.ip500.webide.dto.chatting;

import lombok.Builder;
import lombok.Getter;

/**
 * 채팅 웹소켓 반환 타입
 */
@Builder
@Getter
public class ChattingResponseDto {
    private ChatType messageType;
    private String memberName;
    private String content;
}
