package com.ip500.webide.dto.folderfile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileUpdateRequest {

    private String fileName;
    private String content;
    private String path;
    private Long folderId;

    @Builder
    public FileUpdateRequest(String fileName, String content, String path) {
        this.fileName = fileName;
        this.content = content;
        this.path = path;
//        this.folderId = folderId;
    }

    public FileServiceRequest toServiceRequest() {
        return FileServiceRequest.builder()
                .fileName(fileName)
                .content(content)
                .path(path)
//                .folderId(folderId)
                .build();
    }

}
