package com.ip500.webide.service.chatting;

import com.ip500.webide.dto.chatting.ChatType;
import com.ip500.webide.dto.chatting.ChattingContentRequestDto;
import com.ip500.webide.dto.chatting.ChattingResponseDto;
import com.ip500.webide.domain.chatting.Chat;
import com.ip500.webide.domain.chatting.ChattingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingRepository chattingRepository;

    /**
     * 클라이언트가 채팅방 입장 시 알림 및 세션 등록
     */
    public ChattingResponseDto enter(Long projectId, String memberName) {
        String content = memberName + "님이 참여하였습니다.";

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException("채팅 참여 메시지 Thread.sleep 에러");
        }

        return ChattingResponseDto.builder()
                .messageType(ChatType.ENTER)
                .memberName(memberName)
                .content(content)
                .build();
    }

    /**
     * 클라이언트가 채팅 시 모든 메시지 전송, 데이터베이스에 저장하기
     */
    @Transactional
    public ChattingResponseDto talk(
            ChattingContentRequestDto chattingContentRequestDto,
            String memberName,
            Long projectId
    ) {
        // db에 채팅 기록 저장
        chattingRepository.save(new Chat(chattingContentRequestDto));

        return ChattingResponseDto.builder()
                .messageType(ChatType.TALK)
                .memberName(memberName)
                .content(chattingContentRequestDto.getContent())
                .build();
    }

    /**
     * 클라이언트가 채팅 종료 시 퇴장 알림
     */
    public ChattingResponseDto exit(String memberName, Long projectId) {
        String content = memberName + "님이 퇴장하였습니다.";

        return ChattingResponseDto.builder()
                .messageType(ChatType.EXIT)
                .memberName(memberName)
                .content(content)
                .build();
    }
}