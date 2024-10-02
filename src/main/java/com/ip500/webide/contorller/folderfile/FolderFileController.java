package com.ip500.webide.contorller.folderfile;

import com.ip500.webide.domain.folderfile.File;
import com.ip500.webide.domain.folderfile.FileRepository;
import com.ip500.webide.domain.folderfile.Folder;
import com.ip500.webide.domain.project.Project;
import com.ip500.webide.service.folderfile.FolderFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("projects")
public class FolderFileController {

    private final FolderFileService folderFileService;

    public FolderFileController(FolderFileService folderFileService) {
        this.folderFileService = folderFileService;
    }

    //////////////////////////프로젝트 내 폴더 생성
    @PostMapping("/{projectId}/folders")
    public ResponseEntity<Folder> createFolder(
            @PathVariable Long projectId,
            @RequestBody Map<String, Object> requestBody // JSON 데이터를 Map으로 받음
    ) {
        //JSON에서 folderName과 parentFolderId 추출
        String folderName = (String) requestBody.get("folderName");
        Long parentFolderId = requestBody.get("parentFolderId") != null ?
                Long.valueOf(requestBody.get("parentFolderId").toString()) : null;

        Folder folder = folderFileService.createFolder(folderName, parentFolderId, projectId);

        return ResponseEntity.ok(folder);
    }

    //////////////////////////프로젝트 내 폴더 생성
    @PostMapping("/{projectId}/files")
    public ResponseEntity<File> createFileInProject(
            @PathVariable Long projectId,
            @RequestBody Map<String, String> fileData
    ) {
        String fileName = fileData.get("fileName");
        String content = fileData.get("content");

        File file = folderFileService.createFileInProject(fileName, content, projectId);

        return ResponseEntity.ok(file);
    }

    /////////////////////////폴더 내 파일 생성
    @PostMapping("/{projectId}/folders/{folderId}/files")
    public ResponseEntity<File> createFile(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @RequestBody Map<String, String> fileData //JSON 데이터를 Map으로 받음
    ) {
        //JSON에서 파일명과 내용을 추출
        String fileName = fileData.get("fileName");
        String content = fileData.get("content");

        File file = folderFileService.createFile(fileName, content, folderId, projectId);

        return ResponseEntity.ok(file);
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
            @PathVariable Long projectId,
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
    public ResponseEntity<Folder> patchFolder(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @RequestBody Map<String, Object> requestBody
    ) {
        String folderName = requestBody.containsKey("folderName") ? (String) requestBody.get("folderName") : null;
        Long parentFolderId = requestBody.containsKey("parentFolderId") && requestBody.get("parentFolderId") != null ?  //널포인터에러방지
                Long.valueOf(requestBody.get("parentFolderId").toString()) : null;

        Folder updateFolder = folderFileService.patchFolder(folderId, folderName, parentFolderId, projectId);

        return ResponseEntity.ok(updateFolder);
    }


    /////////////////////////파일 수정
    @PatchMapping("/{projectId}/folders/{folderId}/files/{fileId}")
    public ResponseEntity<File> patchFile(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @PathVariable Long fileId,
            @RequestBody Map<String, Object> requestBody
    ) {
        String fileName = requestBody.containsKey("fileName") ? (String) requestBody.get("fileName") : null;
        String content = requestBody.containsKey("content") ? (String) requestBody.get("content") : null;

        File updateFile = folderFileService.patchFile(fileId, fileName, content, folderId, projectId);

        return ResponseEntity.ok(updateFile);

    }


    /////////////////////////폴더 삭제
    @DeleteMapping("/{projectId}/folders/{folderId}")
    public ResponseEntity<Void> deleteFolder(
            @PathVariable Long projectId,
            @PathVariable Long folderId
    ) {
        folderFileService.deleteFolderWithContents(folderId);
        return ResponseEntity.noContent().build();
    }


    /////////////////////////파일 삭제
    @DeleteMapping("/{projectId}/folders/{folderId}/files/{fileId}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @PathVariable Long fileId
    ) {
        folderFileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }


}
