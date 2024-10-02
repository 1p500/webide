package com.ip500.webide.controller.project.dto.request;

import com.ip500.webide.domain.project.Project;
import com.ip500.webide.service.project.dto.request.ProjectServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // spring validation을 위해 기본 생성자가 필요함
public class ProjectCreateRequest {

    @NotBlank(message = "프로젝트 이름은 필수입니다.")
    private String name;

    private String description;

    @Builder
    private ProjectCreateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProjectServiceRequest toServiceRequest() {
        return ProjectServiceRequest.builder()
            .name(name)
            .description(description)
            .build();
    }

}
