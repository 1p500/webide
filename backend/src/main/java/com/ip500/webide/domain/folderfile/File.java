package com.ip500.webide.domain.folderfile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ip500.webide.domain.project.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "폴더 이름은 필수입니다.")
    private String name;

    private String path;

    private long size;

    @Lob    //큰 객체 저장
    private String content;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = true)
    @JsonIgnore  // 순환 참조 방지
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore  // 순환 참조 방지
    private Project project;

    @Builder
    private File(Long id, String name, String path, long size, String content, Folder folder, Project project) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.size = size;
        this.content = content;
//        this.folderId = folderId;
        this.folder = folder;
        this.project = project;
    }

    @PrePersist
    protected void onCreate() {
        createdDateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDateTime = LocalDateTime.now();
    }

}