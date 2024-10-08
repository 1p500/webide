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
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "폴더 이름은 필수입니다.")
    private String name;

    private String path;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id", nullable = true)
    @JsonIgnore  // 순환 참조 방지
    private Folder parentFolder;


    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL)
    @JsonIgnore  // 순환 참조 방지
    private List<Folder> subFolders;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<File> files;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore  // 순환 참조 방지
    private Project project;

    @Builder
    private Folder(Long id, String name, String path, Folder parentFolder, List<Folder> subFolders, List<File> files, Project project) {
        this.id = id;
        this.name = name;
        this.path = path;
//        this.parentFolderId = parentFolderId;
        this.parentFolder = parentFolder;
        this.subFolders = subFolders;
        this.files = files;
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