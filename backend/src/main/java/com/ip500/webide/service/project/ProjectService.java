package com.ip500.webide.service.project;

import com.ip500.webide.domain.project.Project;
import com.ip500.webide.domain.project.ProjectRepository;
import com.ip500.webide.service.chat.ChatRoomService;
import com.ip500.webide.service.project.dto.request.ProjectServiceRequest;
import com.ip500.webide.service.project.response.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    // TODO: chatRoomService 매핑
    private final ChatRoomService chatRoomService;

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

    public List<Project> getProjectListByOwnerId(Long userId) {
        return projectRepository.findByOwnerId(userId);
    }
}
