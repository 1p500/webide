package com.ip500.webide.dto.folderfile;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FolderServiceRequest {

    private String folderName;
    private String path;
    private Long parentFolderId;

}
