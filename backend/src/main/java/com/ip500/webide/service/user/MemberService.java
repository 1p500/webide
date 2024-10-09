package com.ip500.webide.service.user;

import com.ip500.webide.dto.user.JoinRequest;
import com.ip500.webide.repository.user.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public boolean checkLoginIdDuplicate(String loginId){
        log.info("checkLoginIdDuplicate() - loginId : {}", loginId);
        return memberRepository.existsByUserId(loginId);
    }
    // BCryptPasswordEncoder 를 통해서 비밀번호 암호화 작업 추가한 회원가입 로직
    public void securityJoin(JoinRequest joinRequest){
        log.info("securityJoin() - joinRequest : {}", joinRequest);
        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));
        memberRepository.save(joinRequest.toEntity());
    }
}
