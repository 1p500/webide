package com.ip500.webide.domain.project;

import com.ip500.webide.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
class ProjectRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ProjectRepository projectRepository;

    @DisplayName("프로젝트를 생성하고 저장할 수 있다.")
    @Test
    void saveProject() {
        // given
        Project project = createProject(1L, "테스트 프로젝트", "설명");

        // when
        Project savedProject = projectRepository.save(project);

        // then
        assertThat(savedProject).isNotNull();
        assertThat(savedProject.getId()).isNotNull();
        assertThat(savedProject.getName()).isEqualTo("테스트 프로젝트");
        assertThat(savedProject.getDescription()).isEqualTo("설명");
    }

    @DisplayName("사용자 ID와 프로젝트 이름으로 프로젝트를 조회할 수 있다.")
    @Test
    void duplicateProjectNameShouldFail() {
        // given
        Project project = createProject(1L, "프로젝트 AAA", "이 부분은 설명입니다.");
        projectRepository.save(project);

        // when
        Optional<Project> foundProject = projectRepository.findByOwnerIdAndName(1L, "프로젝트 AAA");

        // then
        assertThat(foundProject).isPresent();
        assertThat(foundProject.get().getName()).isEqualTo("프로젝트 AAA");
        assertThat(foundProject.get().getDescription()).isEqualTo("이 부분은 설명입니다.");
    }

    @DisplayName("존재하지 않는 사용자 ID와 프로젝트 이름으로 조회 시 빈 값을 반환한다.")
    @Test
    void findByOwnerIdAndName_Fail() {
        // given
        Long ownerId = 1L;
        String projectName = "빈 프로젝트";

        // when
        Optional<Project> foundProject = projectRepository.findByOwnerIdAndName(ownerId, projectName);

        // then
        assertThat(foundProject).isEmpty();
    }

    @DisplayName("사용자의 ID로 프로젝트 목록을 조회할 수 있다.")
    @Test
    void findByOwnerId() {
        // given
        Long userId = 1L;
        Project project1 = createProject(userId, "프로젝트 1", "설명 1");
        Project project2 = createProject(userId, "프로젝트 2", "설명 2");
        Project project3 = createProject(2L, "프로젝트 3", "설명 3");

        projectRepository.saveAll(List.of(project1, project2, project3));

        // when
        List<Project> projects = projectRepository.findByOwnerId(userId);

        // then
        assertThat(projects).hasSize(2)
                            .extracting("ownerId", "name", "description")
                            .containsExactlyInAnyOrder(
                                    tuple(1L, "프로젝트 1", "설명 1"),
                                    tuple(1L, "프로젝트 2", "설명 2")
                            );
    }

    @DisplayName("프로젝트 정보를 변경할 수 있다.")
    @Test
    void updateProject() {
        // given
        Project project = createProject(1L, "프로젝트", "설명");
        projectRepository.save(project);

        // when
        project.updateProjectInfo("프로젝트 수정", "설명 수정");
        Project updatedProject = projectRepository.save(project);

        // then
        assertThat(updatedProject.getName()).isEqualTo("프로젝트 수정");
        assertThat(updatedProject.getDescription()).isEqualTo("설명 수정");
    }

    private Project createProject(Long ownerId, String name, String description) {
        return Project.builder()
                      .ownerId(ownerId)
                      .name(name)
                      .description(description)
                      .build();
    }
}