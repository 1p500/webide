package com.ip500.webide.service.project;

import com.ip500.webide.domain.project.Project;
import com.ip500.webide.domain.project.ProjectRepository;
import com.ip500.webide.service.chat.ChatRoomService;
import com.ip500.webide.service.project.dto.request.ProjectServiceRequest;
import com.ip500.webide.service.project.response.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    // TODO: chatRoomService 매핑
    private final ChatRoomService chatRoomService;

    @Transactional
    public ProjectResponse createProject(Long userId, ProjectServiceRequest request) {
        Project project = request.toEntity(userId);
        Project savedProject = projectRepository.save(project);

        Long chatRoomId = chatRoomService.createChatRoom(savedProject.getId());
        System.out.println(chatRoomId);
        return ProjectResponse.of(savedProject, chatRoomId);
    }
}
