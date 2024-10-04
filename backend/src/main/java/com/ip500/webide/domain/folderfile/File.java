package com.ip500.webide.domain.folderfile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ip500.webide.domain.project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@Setter
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull//(message = "폴더 이름은 필수입니다.")
    private String name;

    private String path;

    private long size;

    @Lob    //큰 객체 저장
    private String content;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = true)
    @JsonIgnore  // 순환 참조 방지
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore  // 순환 참조 방지
    private Project project;
}
