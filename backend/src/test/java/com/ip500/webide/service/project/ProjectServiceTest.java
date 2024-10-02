package com.ip500.webide.service.project;

import com.ip500.webide.IntegrationTestSupport;
import com.ip500.webide.domain.project.Project;
import com.ip500.webide.domain.project.ProjectRepository;
import com.ip500.webide.service.project.dto.request.ProjectServiceRequest;
import com.ip500.webide.service.project.response.ProjectResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ProjectServiceTest extends IntegrationTestSupport {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @AfterEach
    void tearDown() {
        projectRepository.deleteAllInBatch();
    }

    @DisplayName("프로젝트 생성 시 프로젝트 이름과 설명이 올바르게 저장된다.")
    @Test
    void createProject() {
        // given
        Project project1 = createProject(1L, "테스트 프로젝트1", "첫 번째 프로젝트입니다.");
        Project project2 = createProject(2L, "테스트 프로젝트2", "두 번째 프로젝트입니다.");
        projectRepository.saveAll(List.of(project1, project2));

        ProjectServiceRequest request = ProjectServiceRequest.builder()
                                                             .name("테스트 프로젝트 2222")
                                                             .description("")
                                                             .build();
        // when
        ProjectResponse projectResponse = projectService.createProject(1L, request);
        List<Project> projects = projectRepository.findAll();

        // then
        assertThat(projectResponse)
                .extracting("name", "description")
                .contains("테스트 프로젝트 2222", "");

        assertThat(projects).hasSize(3)
                            .extracting("name", "description", "ownerId")
                            .contains(
                                    tuple("테스트 프로젝트1", "첫 번째 프로젝트입니다.", 1L),
                                    tuple("테스트 프로젝트2", "두 번째 프로젝트입니다.", 2L),
                                    tuple("테스트 프로젝트 2222", "", 1L)
                            );
    }

    @DisplayName("중복된 이름으로 프로젝트를 생성할 수 없다.")
    @Test
    void createProjectWithDuplicateNameInService() {
        // given
        projectRepository.save(Project.builder()
                                      .ownerId(1L)
                                      .name("중복된 프로젝트")
                                      .description("기존 프로젝트")
                                      .build());

        ProjectServiceRequest request = ProjectServiceRequest.builder()
                                                             .name("중복된 프로젝트")
                                                             .description("새로운 프로젝트")
                                                             .build();

        // when & then
        assertThatThrownBy(() -> projectService.createProject(1L, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 동일한 이름의 프로젝트가 존재합니다.");
    }

    private Project createProject(Long ownerId, String name, String description) {
        return Project.builder()
                .ownerId(ownerId)
                .name(name)
                .description(description)
                .build();
    }
}
