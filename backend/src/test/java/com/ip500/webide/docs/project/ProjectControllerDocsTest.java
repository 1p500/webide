package com.ip500.webide.docs.project;

import com.ip500.webide.controller.project.ProjectController;
import com.ip500.webide.controller.project.dto.request.ProjectCreateRequest;
import com.ip500.webide.docs.RestDocsSupport;
import com.ip500.webide.service.project.ProjectService;
import com.ip500.webide.service.project.dto.request.ProjectServiceRequest;
import com.ip500.webide.service.project.response.ProjectResponse;
import com.ip500.webide.service.user.IUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerDocsTest extends RestDocsSupport {

    private final ProjectService projectService = mock(ProjectService.class);
    private final IUserService userService = mock(IUserService.class);

    @Override
    protected Object initController() {
        return new ProjectController(projectService, userService);
    }

    @DisplayName("프로젝트 생성 API")
    @Test
    void createProject() throws Exception {
        ProjectCreateRequest request = ProjectCreateRequest.builder()
                                                           .name("테스트 프로젝트")
                                                           .description("이 프로젝트는 ~~ 입니다.")
                                                           .build();

        given(projectService.createProject(any(Long.class), any(ProjectServiceRequest.class)))
                .willReturn(
                        ProjectResponse.builder()
                                       .id(1L)
                                       .name("테스트 프로젝트")
                                       .description("이 프로젝트는 ~~ 입니다.")
                                       .chatRoomId(1L)
                                       .build()
                );

        mockMvc.perform(post("/project/create")
                       .content(objectMapper.writeValueAsString(request))
                       .contentType(MediaType.APPLICATION_JSON)
               )
               .andDo(print())
               .andExpect(status().isOk())
               .andDo(document("project-create",
                       preprocessRequest(prettyPrint()),
                       preprocessResponse(prettyPrint()),
                       requestFields(
                               fieldWithPath("name").type(JsonFieldType.STRING)
                                                    .description("프로젝트 이름"),
                               fieldWithPath("description").type(JsonFieldType.STRING)
                                                           .optional()
                                                           .description("프로젝트 설명")
                       ),
                       responseFields(
                               fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                    .description("응답 코드"),
                               fieldWithPath("status").type(JsonFieldType.STRING)
                                                      .description("응답 상태"),
                               fieldWithPath("message").type(JsonFieldType.STRING)
                                                       .description("응답 메시지"),
                               fieldWithPath("data").type(JsonFieldType.OBJECT)
                                                    .description("응답 데이터"),
                               fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                                       .description("프로젝트 ID"),
                               fieldWithPath("data.name").type(JsonFieldType.STRING)
                                                         .description("프로젝트 이름"),
                               fieldWithPath("data.description").type(JsonFieldType.STRING)
                                                                .description("프로젝트 설명"),
                               fieldWithPath("data.chatRoomId").type(JsonFieldType.NUMBER)
                                                               .description("채팅방 ID")
                       )));
    }
}
