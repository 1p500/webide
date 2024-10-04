package com.ip500.webide.controller.project;

import com.ip500.webide.common.ApiResponse;
import com.ip500.webide.dto.project.request.ProjectCreateRequest;
import com.ip500.webide.service.project.ProjectService;
import com.ip500.webide.dto.project.response.ProjectResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 프로젝트 생성 API
     * @param request 프로젝트 생성 요청 DTO
     * @param httpRequest HTTP 요청 객체 (토큰 추출용)
     * @return ProjectResponse 생성된 프로젝트 응답 DTO
     */
    @PostMapping("/create")
    public ApiResponse<ProjectResponse> createProject(
            @Valid @RequestBody ProjectCreateRequest request,
            HttpServletRequest httpRequest
    ) {
        String token = getToken(httpRequest);
        Long memberId = getMemberIdFromToken(token);

        return ApiResponse.ok(projectService.createProject(memberId, request.toServiceRequest()));
    }

    /**
     * 프로젝트 리스트 조회 API
     * @param httpRequest HTTP 요청 객체 (토큰 추출용)
     * @return List<ProjectResponse> 프로젝트 응답 DTO 리스트
     */
    @GetMapping("/list")
    public ApiResponse<List<ProjectResponse>> getProjectListByOwnerId(HttpServletRequest httpRequest) {
        String token = getToken(httpRequest);
        Long memberId = getMemberIdFromToken(token);
        return ApiResponse.ok(projectService.getProjectListByOwnerId(memberId));
    }

    /**
     * 프로젝트 수정 API
     * @param projectId 수정할 프로젝트 ID
     * @param request 수정할 프로젝트 정보
     * @param httpRequest HTTP 요청 객체 (토큰 추출용)
     * @return ProjectResponse 수정된 프로젝트 응답 DTO
     */
    @PutMapping("/{projectId}")
    public ApiResponse<ProjectResponse> updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectCreateRequest request,
            HttpServletRequest httpRequest
    ) {
        String token = getToken(httpRequest);
        Long memberId = getMemberIdFromToken(token);
        return ApiResponse.ok(projectService.updateProject(memberId, projectId, request.toServiceRequest()));
    }

    /**
     * HTTP 요청에서 토큰 추출
     * @param httpRequest HTTP 요청 객체 (헤더 추출용)
     * @return jwt 토큰
     */
    private String getToken(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
        }
        return token;
    }

    /**
     * 토큰에서 memberId 추출
     * @param token jwt 토큰
     * @return memberId
     */
    private Long getMemberIdFromToken(String token) {
        String SECRET_KEY = System.getenv("JWT_SECRET");
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        Claims claims = Jwts.parser().verifyWith(secretKey)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();

        return claims.get("id", Long.class);
    }
}
