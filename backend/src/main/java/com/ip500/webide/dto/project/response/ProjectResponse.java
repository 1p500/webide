package com.ip500.webide.dto.project.response;

import com.ip500.webide.domain.project.Project;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;

    @Builder
    private ProjectResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static ProjectResponse of(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .build();
    }
}
