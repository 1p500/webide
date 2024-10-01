package com.ip500.webide.service.project.response;

import com.ip500.webide.domain.project.Project;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private Long chatRoomId;

    @Builder
    private ProjectResponse(Long id, String name, String description, Long chatRoomId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.chatRoomId = chatRoomId;
    }

    public static ProjectResponse of(Project project, Long chatRoomId) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .chatRoomId(chatRoomId)
                .build();
    }
}
