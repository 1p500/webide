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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    // 프로젝트 생성자
    // TODO: User 관련 테이블과 매핑 (1:N)
    private Long ownerId;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @Builder
    private Project(Long id, String name, String description, Long ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
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
