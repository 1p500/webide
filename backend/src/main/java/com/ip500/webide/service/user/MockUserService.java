package com.ip500.webide.service.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mock")
public class MockUserService implements IUserService {

    @Override
    public boolean isLoggedIn() {
        return true;
    }

    @Override
    public Long getUserId() {
        return 1L;
    }
}
