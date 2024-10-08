package com.ip500.webide.dto.folderfile;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileServiceRequest {

    private String fileName;
    private String content;
    private String path;
//    private Long folderId;

}
