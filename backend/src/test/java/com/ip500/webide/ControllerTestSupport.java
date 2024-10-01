package com.ip500.webide;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ip500.webide.controller.project.ProjectController;
import com.ip500.webide.service.project.ProjectService;
import com.ip500.webide.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        ProjectController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ProjectService projectService;

    @MockBean
    protected IUserService userService;
}
