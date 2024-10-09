package com.ip500.webide.dto.chatting;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 웹소켓 세션에 포함될 내용
 */
@Slf4j
@Getter
public class WebSocketMember {
    private final MemberInfoDto memberInfoDto;
    private final Long projectId;
    private final ConcurrentHashMap.KeySetView<String, Boolean> subscribes;

    /**
     * 생성자
     * */
    public WebSocketMember(MemberInfoDto memberInfoDto, Long projectId) {
        this.memberInfoDto = memberInfoDto;
        this.projectId = projectId;
        this.subscribes = ConcurrentHashMap.newKeySet(); // 키를 이용한 Set 구현.
    }

    /**
     * 구독타입 저장하기
     * */
    public void startSubscribe(String subscribeType){
        this.subscribes.add(subscribeType);
    }

    /**
     * 구독하고 있는지 확인하는 메서드
     * */
    public boolean isSubscribe(String subscribeType){
        // 구독하고 있다면 true, 안하면 false
        return this.subscribes.stream().anyMatch(s -> s.equals(subscribeType));
    }

    /**
     * memberId와 projectId 두개가 동일한 객체가 존재하는지 비교문
     * */
    public boolean isSameWith(Long memberId, Long projectId){
        return this.memberInfoDto.getId().equals(memberId) && this.projectId.equals(projectId);
    }
}
