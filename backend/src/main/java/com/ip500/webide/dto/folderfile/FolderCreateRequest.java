package com.ip500.webide.dto.folderfile;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderCreateRequest {

    @NotNull(message = "폴더 이름은 필수입니다.")
    private String folderName;
    private String path;
    private Long parentFolderId;

    @Builder
    private FolderCreateRequest(String folderName, Long parentFolderId, String path) {
        this.folderName = folderName;
        this.path = path;
        this.parentFolderId = parentFolderId;
    }

    public FolderServiceRequest toServiceRequest() {
        return FolderServiceRequest.builder()
                .folderName(folderName)
                .path(path)
                .parentFolderId(parentFolderId)
                .build();
    }
}
