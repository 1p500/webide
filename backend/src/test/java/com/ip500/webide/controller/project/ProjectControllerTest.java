package com.ip500.webide.controller.project;

import com.ip500.webide.ControllerTestSupport;
import com.ip500.webide.controller.project.dto.request.ProjectCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerTest extends ControllerTestSupport {


    @DisplayName("신규 프로젝트를 생성한다.")
    @Test
    void createProject() throws Exception {
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
               .andExpect(status().isOk());
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
}