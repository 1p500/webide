package com.ip500.webide.service.user;

import com.ip500.webide.domain.user.Member;
import com.ip500.webide.domain.user.MemberRole;
import com.ip500.webide.jwt.CustomOauth2UserDetails;
import com.ip500.webide.jwt.oauth2.GithubUserDetails;
import com.ip500.webide.jwt.oauth2.GoogleUserDetails;
import com.ip500.webide.jwt.oauth2.OAuth2UserInfo;
import com.ip500.webide.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserDetailsService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User loadUser() - getAttributes : {}", oAuth2User.getAttributes());
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;
        // 소셜 서비스 로그인 구분
        switch(provider){
            case "google":
                log.info("구글 로그인");
                oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
                break;
            case "github":
                log.info("깃허브 로그인");
                oAuth2UserInfo = new GithubUserDetails(oAuth2User.getAttributes());
                break;
        }
        String providerId = oAuth2UserInfo.getProviderId();
        String loginId = oAuth2UserInfo.getEmail();
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();
        Member findMember = memberRepository.findByUserId(loginId);
        Member member;
        if (findMember == null) {
            member = Member.builder()
                    .userId(loginId)
                    .userName(name)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .role(MemberRole.USER)
                    .createdUserDate(new Date())
                    .build();
            memberRepository.save(member);
        } else{
            member = findMember;
        }
        return new CustomOauth2UserDetails(member, oAuth2User.getAttributes());
    }
}
