package com.ip500.webide.service.project;

import com.ip500.webide.domain.project.Project;
import com.ip500.webide.domain.project.ProjectRepository;
import com.ip500.webide.service.chat.ChatRoomService;
import com.ip500.webide.dto.project.request.ProjectServiceRequest;
import com.ip500.webide.dto.project.response.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    // TODO: chatRoomService 매핑
    private final ChatRoomService chatRoomService;

    /**
     * 프로젝트 생성
     * @param userId 사용자 ID
     * @param request 프로젝트 생성 요청 DTO
     * @return ProjectResponse 생성된 프로젝트 응답 DTO
     */
    @Transactional
    public ProjectResponse createProject(Long userId, ProjectServiceRequest request) {

        // 유저가 동일한 프로젝트가 있을 경우 예외 처리
        Optional<Project> existingProject = projectRepository.findByOwnerIdAndName(userId, request.getName());

        if (existingProject.isPresent()) {
            throw new IllegalArgumentException("이미 동일한 이름의 프로젝트가 존재합니다.");
        }

        Project project = request.toEntity(userId);
        Project savedProject = projectRepository.save(project);

        // 프로젝트 생성시 채팅방 생성
        chatRoomService.createChatRoom(savedProject.getId());

        return ProjectResponse.of(savedProject);
    }

    /**
     * 사용자 ID로 프로젝트 리스트 조회
     * @param userId 사용자 ID
     * @return List<ProjectResponse> 프로젝트 응답 DTO 리스트
     */
    public List<ProjectResponse> getProjectListByOwnerId(Long userId) {
        List<Project> projects = projectRepository.findByOwnerId(userId);

        return projects.stream()
                       .map(ProjectResponse::of)
                       .collect(Collectors.toList());
    }

    /**
     * 프로젝트 수정
     * @param userId 사용자 ID
     * @param projectId 프로젝트 ID
     * @param serviceRequest 프로젝트 수정 요청 DTO
     * @return ProjectResponse 수정된 프로젝트 응답 DTO
     */
    @Transactional
    public ProjectResponse updateProject(Long userId, Long projectId, ProjectServiceRequest serviceRequest) {
        Project project = projectRepository.findById(projectId)
                                           .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

        if (!project.getOwnerId().equals(userId)) {
            throw new IllegalArgumentException("프로젝트의 소유자가 아닙니다.");
        }

        project.updateProjectInfo(serviceRequest.getName(), serviceRequest.getDescription());
        Project updatedProject = projectRepository.save(project);

        return ProjectResponse.of(updatedProject);
    }
}
