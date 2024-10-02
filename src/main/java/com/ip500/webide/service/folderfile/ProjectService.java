package com.ip500.webide.service.folderfile;

import com.ip500.webide.domain.project.Project;
import com.ip500.webide.domain.project.ProjectRepository;
import org.springframework.stereotype.Service;


/////////////////////////////TEST///////////////////////
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    // 의존성 주입
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // 프로젝트 생성 및 저장 메서드
    public Project createAndSaveProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("test");

        // 프로젝트 저장
        return projectRepository.save(project);
    }
}
