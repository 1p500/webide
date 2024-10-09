package com.ip500.webide.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static String domain = "http://localhost:3000"; // 로컬용
    //    public static String domain = "[도메인]"; // 배포용

    public static String home = domain;
    public static String login = domain + "/login";
    public static String signup = domain + "/signup";
    public static String google_oauth2 = "/oauth2/authorization/google";
    public static String github_oauth2 = "/oauth2/authorization/github";
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8080", domain) // 허용할 출처
                .allowedMethods("GET", "POST", "FATCH", "PUT", "DELETE") // 허용할 HTTP method
                .allowedHeaders("Authorization", "RefreshToken", "Content-Type")
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }
}