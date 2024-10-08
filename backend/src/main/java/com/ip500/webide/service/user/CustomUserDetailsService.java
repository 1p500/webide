package com.ip500.webide.service.user;

import com.ip500.webide.domain.user.Member;
import com.ip500.webide.jwt.FormUserDetails;
import com.ip500.webide.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("UserDetails loadUserByUsername() - username(loginId) : {}", loginId);
        Member member = memberRepository.findByUserId(loginId);
        if(member != null) {
            return new FormUserDetails(member);
        }
        return null;
    }
}