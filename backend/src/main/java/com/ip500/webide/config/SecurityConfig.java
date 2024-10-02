package com.ip500.webide.config;

import com.ip500.webide.jwt.CustomUserDetails;
import com.ip500.webide.jwt.JWTUtil;
import com.ip500.webide.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    public String domain = WebConfig.domain;
    private final AuthenticationConfiguration configuration;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 같은 origin에서 접근 허용
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        // api 접근 권한 설정
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(PathRequest.toH2Console()).permitAll() // h2console 접근 모두 허용
                // [/**] : 뒤에 붙은 모든 경로 포함
//                .requestMatchers(HttpMethod.[GET/POST/PATCH/PUT/DELETE], domain + "[api uri]/**").hasAuthority(MemberRole.ADMIN.name()) // 권한지정
//                .requestMatchers(HttpMethod.[GET/POST/PATCH/PUT/DELETE], domain + "[api uri]/**").authenticated() // 로그인만 한다면 모든 사용자가 접근 가능
                .anyRequest().permitAll());
        // 폼 로그인 방식 설정
        http.formLogin((auth) -> auth
                .loginPage(domain + "/login")
                .loginProcessingUrl("/api/auth/login/form")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .defaultSuccessUrl(domain)
                .successHandler((request, response, authentication)->{
                    CustomUserDetails principal = (CustomUserDetails)authentication.getPrincipal();
                    log.info("formLogin successHandler auth : {}", principal);
                    // AT RT 생성
                    Long id = principal.getPKId();
                    String loginId = principal.getUsername();
                    String memberRole = principal.getRole().name();
                    String accessToken = jwtUtil.createAccessToken(id, loginId, memberRole);
                    String refreshToken = jwtUtil.createRefreshToken();

                    response.addHeader("Authorization", "Bearer " + accessToken);
                    response.addHeader("RefreshToken", "Bearer " + refreshToken);
//                    response.sendRedirect(domain + "/home");
                })
                .failureUrl("/oauth2-login/login")
                .failureHandler((request, response, authentication)->{
                    log.info("formLogin failureHandler\nrequest : {}\nresponse : {}\nauthentication : {}\n\n", request, response, authentication);
                    response.sendRedirect(domain + "/login");
                })
                .permitAll());
        // 로그아웃 URL 설정
        http.logout((auth) -> auth
//                .logoutUrl("[api logout uri]")
                .logoutSuccessUrl(domain)); // 로그아웃 성공 시 redirect
        // csrf : 사이트 위변조 방지 설정 (스프링 시큐리티에는 자동으로 설정 되어 있음)
        // csrf기능 켜져있으면 post 요청을 보낼때 csrf 토큰도 보내줘야 로그인 진행됨 !
        // 개발단계에서만 csrf 잠시 꺼두기
        http.csrf((auth) -> auth.disable());
        // http basic 인증 방식 disable 설정
        http.httpBasic((auth -> auth.disable()));
        // 세션 설정
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//         새로 만든 로그인 필터를 원래의 (UsernamePasswordAuthenticationFilter)의 자리에 넣음
        http.addFilterAt(new LoginFilter(authenticationManager(configuration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        // 로그인 필터 이전에 JWTFilter를 넣음
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){ return new BCryptPasswordEncoder(); }
}
