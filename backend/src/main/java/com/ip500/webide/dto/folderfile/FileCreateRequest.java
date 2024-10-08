package com.ip500.webide.dto.folderfile;

import com.ip500.webide.domain.folderfile.File;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileCreateRequest {

    @NotNull(message = "파일 이름은 필수입니다")
    private String fileName;
    private String content;
    private String path;
//    private Long folderId;

    @Builder
    private FileCreateRequest(String fileName, String content, String path) {
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
