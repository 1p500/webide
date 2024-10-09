package com.ip500.webide.dto.folderfile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderUpdateRequest {

    private String folderName;
    private Long parentFolderId;
    private String path;

    @Builder
    public FolderUpdateRequest(String folderName, Long parentFolderId, String path) {
        this.folderName = folderName;
        this.parentFolderId = parentFolderId;
        this.path = path;
    }

    public FolderServiceRequest toServiceRequest() {
        return FolderServiceRequest.builder()
                .folderName(folderName)
                .parentFolderId(parentFolderId)
                .path(path)
                .build();
    }
}
