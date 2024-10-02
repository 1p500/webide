package com.ip500.webide.controller.project;

import com.ip500.webide.common.ApiResponse;
import com.ip500.webide.dto.project.request.ProjectCreateRequest;
import com.ip500.webide.service.project.ProjectService;
import com.ip500.webide.dto.project.response.ProjectResponse;
import com.ip500.webide.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    @Qualifier("mock")
    private final IUserService userService;

    @PostMapping("/create")
    public ApiResponse<ProjectResponse> createProject(@Valid @RequestBody ProjectCreateRequest request) {
        Long userId = userService.getUserId();
        return ApiResponse.ok(projectService.createProject(userId, request.toServiceRequest()));
    }

    @GetMapping("/list")
    public ApiResponse<List<ProjectResponse>> getProjectListByOwnerId() {
        Long userId = userService.getUserId();
        return ApiResponse.ok(projectService.getProjectListByOwnerId(userId));
    }

    @PutMapping("/{projectId}")
    public ApiResponse<ProjectResponse> updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectCreateRequest request) {
        Long userId = userService.getUserId();
        return ApiResponse.ok(projectService.updateProject(userId, projectId, request.toServiceRequest()));
    }

}
