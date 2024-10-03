package com.ip500.webide.domain.folderfile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ip500.webide.domain.project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Getter
@Setter
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull//(message = "폴더 이름은 필수입니다.")
    private String name;

    private String path;

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
}
