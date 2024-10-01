package com.ip500.webide.domain.project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private String description;

    // 프로젝트 생성자
    // TODO: User 관련 테이블과 매핑 (1:N)
    private Long ownerId;

    // TODO: Chat 관련 테이블과 매핑 (1:1)
    private Long chatId;

    // TODO: Folder 관련 테이블과 매핑 (1:N)
    private Long folderId;

    // TODO: File 관련 테이블과 매핑 (1:N)
    private Long fileId;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @Builder
    private Project(String name, String description, Long ownerId, Long chatId) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.chatId = chatId;
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
