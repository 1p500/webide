package com.ip500.webide.service.chat;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier
public class MockChatRoomService implements ChatRoomService {

    @Override
    public Long createChatRoom(Long projectId) {
        System.out.println("MockChatRoomService.createChatRoom" + projectId);

        return 1L;
    }

}
