package com.ip500.webide.jwt;

import com.ip500.webide.domain.user.MemberRole;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    public Long getPKId();
    public MemberRole getRole();
}
