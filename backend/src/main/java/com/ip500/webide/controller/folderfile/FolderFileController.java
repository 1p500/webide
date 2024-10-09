package com.ip500.webide.controller.folderfile;

import com.ip500.webide.domain.folderfile.File;
import com.ip500.webide.domain.folderfile.Folder;
import com.ip500.webide.dto.folderfile.*;
import com.ip500.webide.service.folderfile.FolderFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class FolderFileController {

    private final FolderFileService folderFileService;

    public FolderFileController(FolderFileService folderFileService) {
        this.folderFileService = folderFileService;
    }

    //////////////////////////프로젝트 내 폴더 생성
    @PostMapping("/{projectId}/folders")
    public ResponseEntity<Folder> createFolder(
            @PathVariable Long projectId,
            @RequestBody FolderCreateRequest folderCreateRequest
    ) {

        FolderServiceRequest serviceRequest = folderCreateRequest.toServiceRequest();
        Folder folder = folderFileService.createFolder(serviceRequest.getFolderName(), serviceRequest.getParentFolderId(), projectId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{folderId}")
                .buildAndExpand(folder.getId())
                .toUri();

        return ResponseEntity.created(location).body(folder);
    }

    //////////////////////////프로젝트 내 파일 생성
    @PostMapping("/{projectId}/files")
    public ResponseEntity<File> createFileInProject(
            @PathVariable Long projectId,
            @RequestBody FileCreateRequest fileCreateRequest
    ) {

        FileServiceRequest serviceRequest = fileCreateRequest.toServiceRequest();
        File file = folderFileService.createFileInProject(serviceRequest.getFileName(), serviceRequest.getContent(), projectId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{folderId}")
                .buildAndExpand(file.getId())
                .toUri();

        return ResponseEntity.created(location).body(file);
    }

    /////////////////////////폴더 내 파일 생성
    @PostMapping("/{projectId}/folders/{folderId}/files")
    public ResponseEntity<File> createFile(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @RequestBody FileCreateRequest fileCreateRequest
    ) {

        FileServiceRequest serviceRequest = fileCreateRequest.toServiceRequest();
        File file = folderFileService.createFile(serviceRequest.getFileName(), serviceRequest.getContent(), folderId, projectId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{fileId}")
                .buildAndExpand(file.getId())
                .toUri();

        return ResponseEntity.created(location).body(file);
    }


    /////////////////////////프로젝트 내 폴더, 파일 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<Map<String, Object>> getProjectFoldersAndFiles(
            @PathVariable Long projectId
    ) {
        //프로젝트 내 폴더조회(parentFolder == null)
        List<Folder> rootfolders = folderFileService.getFoldersByProjectIdAndParentFolderIsNull(projectId);

        //프로젝트 내 파일조회
        List<File> projectFiles = folderFileService.getFilesByProjectIdAndNoFolder(projectId);

        Map<String, Object> response = new HashMap<>();
        response.put("folders", rootfolders);
        response.put("files", projectFiles);

        return ResponseEntity.ok(response);
    }


    /////////////////////////폴더 내 폴더,파일 조회
    @GetMapping("/{projectId}/folders/{folderId}")
    public ResponseEntity<Map<String, Object>> getFolderFoldersAndFiles(
            @PathVariable Long folderId
    ) {
        //폴더 내 하위폴더 조회
        List<Folder> subFolders = folderFileService.getSubFolderByFolderId(folderId);

        //폴더 내 파일 조회
        List<File> files = folderFileService.getFilesByFolderId(folderId);

        Map<String, Object> response = new HashMap<>();
        response.put("subFolders", subFolders);
        response.put("files", files);

        return ResponseEntity.ok(response);

    }


    /////////////////////////폴더 수정
    @PatchMapping("/{projectId}/folders/{folderId}")
    public ResponseEntity<Folder> updateFolder(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @RequestBody FolderUpdateRequest folderUpdateRequest
    ) {

        FolderServiceRequest serviceRequest = folderUpdateRequest.toServiceRequest();
        Folder updateFolder = folderFileService.updateFolder(folderId, serviceRequest.getFolderName(), serviceRequest.getParentFolderId(), projectId);

        return ResponseEntity.ok(updateFolder);
    }


    /////////////////////////프로젝트내 파일 수정
    @PatchMapping("/{projectId}/files/{fileId}")
    public ResponseEntity<File> updateFileInProject(
            @PathVariable Long projectId,
            @PathVariable Long fileId,
            @RequestBody FileUpdateRequest fileUpdateRequest
    ) {

        Long folderId = null;
        FileServiceRequest serviceRequest = fileUpdateRequest.toServiceRequest();
        File updateFile = folderFileService.updateFile(fileId, serviceRequest.getFileName(), serviceRequest.getContent(), serviceRequest.getPath(), folderId, projectId);


        return ResponseEntity.ok(updateFile);

    }

    /////////////////////////파일 수정
    @PatchMapping("/{projectId}/folders/{folderId}/files/{fileId}")
    public ResponseEntity<File> updateFile(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @PathVariable Long fileId,
            @RequestBody FileUpdateRequest fileUpdateRequest
    ) {

        FileServiceRequest serviceRequest = fileUpdateRequest.toServiceRequest();
        File updateFile = folderFileService.updateFile(fileId, serviceRequest.getFileName(), serviceRequest.getContent(), serviceRequest.getPath(), folderId, projectId);

        return ResponseEntity.ok(updateFile);

    }


    /////////////////////////폴더 삭제
    @DeleteMapping("/{projectId}/folders/{folderId}")
    public ResponseEntity<Void> deleteFolder(
            //@PathVariable Long projectId,
            @PathVariable Long folderId
    ) {
        folderFileService.deleteFolderWithContents(folderId);
        return ResponseEntity.noContent().build();
    }


    /////////////////////////파일 삭제
    @DeleteMapping("/{projectId}/folders/{folderId}/files/{fileId}")
    public ResponseEntity<Void> deleteFile(
            //@PathVariable Long projectId,
            //@PathVariable Long folderId,
            @PathVariable Long fileId
    ) {
        folderFileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }


}
