package com.ip500.webide.controller.project;

import com.ip500.webide.ControllerTestSupport;
import com.ip500.webide.controller.project.dto.request.ProjectCreateRequest;
import com.ip500.webide.domain.project.Project;
import com.ip500.webide.service.project.response.ProjectResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerTest extends ControllerTestSupport {

    @DisplayName("신규 프로젝트를 생성한다.")
    @Test
    void createProjectTest() throws Exception {
        // given
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                                                           .name("테스트 프로젝트")
                                                           .description("이 프로젝트는 ~~ 입니다.")
                                                           .build();

        // when & then
        mockMvc.perform(
                       post("/project/create")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value("200"))
               .andExpect(jsonPath("$.status").value("OK"))
               .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("프로젝트를 생성할 때 이름은 필수값이다.")
    @Test
    void createProjectWithoutName() throws Exception {
        // given
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                                                           .description("이 프로젝트는 ~~ 입니다.")
                                                           .build();

        // when & then
        mockMvc.perform(
                       post("/project/create")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.code").value("400"))
               .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
               .andExpect(jsonPath("$.message").value("프로젝트 이름은 필수입니다."))
               .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("사용자가 소유한 프로젝트 목록을 조회한다.")
    @Test
    void getProjectListByOwnerId() throws Exception {
        // given
        Long userId = 1L;
        List<ProjectResponse> response = List.of(
                ProjectResponse.of(createProject(userId, "테스트 프로젝트 1", "설명 1")),
                ProjectResponse.of(createProject(userId, "테스트 프로젝트 2", "설명 2")),
                ProjectResponse.of(createProject(2L, "테스트 프로젝트 3", "설명 3"))
        );

        given(userService.getUserId()).willReturn(userId);
        given(projectService.getProjectListByOwnerId(userId)).willReturn(response);

        // when & then
        mockMvc.perform(
                       get("/project/list")
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value("200"))
               .andExpect(jsonPath("$.status").value("OK"))
               .andExpect(jsonPath("$.message").value("OK"))
               .andExpect(jsonPath("$.data[0].name").value("테스트 프로젝트 1"))
               .andExpect(jsonPath("$.data[0].description").value("설명 1"))
               .andExpect(jsonPath("$.data[1].name").value("테스트 프로젝트 2"))
               .andExpect(jsonPath("$.data[1].description").value("설명 2"));
    }

    @DisplayName("사용자가 소유한 프로젝트가 없을 때 빈 목록을 반환한다.")
    @Test
    void getEmptyProjectListByOwnerId() throws Exception {
        // given
        Long userId = 1L;
        given(userService.getUserId()).willReturn(userId);
        given(projectService.getProjectListByOwnerId(userId)).willReturn(List.of());

        // when & then
        mockMvc.perform(get("/project/list").contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.code").value("200"))
               .andExpect(jsonPath("$.status").value("OK"))
               .andExpect(jsonPath("$.message").value("OK"))
               .andExpect(jsonPath("$.data").isEmpty());
    }

    private Project createProject(Long ownerId, String name, String description) {
        return Project.builder()
                      .ownerId(ownerId)
                      .name(name)
                      .description(description)
                      .build();

    }
}
