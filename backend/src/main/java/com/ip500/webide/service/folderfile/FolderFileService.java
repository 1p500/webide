package com.ip500.webide.service.folderfile;

import com.ip500.webide.domain.folderfile.File;
import com.ip500.webide.domain.folderfile.FileRepository;
import com.ip500.webide.domain.folderfile.Folder;
import com.ip500.webide.domain.folderfile.FolderRepository;
import com.ip500.webide.domain.project.Project;
import com.ip500.webide.domain.project.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FolderFileService {

    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final ProjectRepository projectRepository;

    public FolderFileService(FileRepository fileRepository, FolderRepository folderRepository,ProjectRepository projectRepository) {
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * 폴더 생성
     * @param folderName 생성할 폴더의 이름
     * @param parentFolderId 부모 폴더의 ID (최상위 폴더 = null)
     * @param projectId 폴더가 속한 프로젝트의 ID
     * @return 생성된 Folder 객체
     */

    @Transactional
    public Folder createFolder(String folderName, Long parentFolderId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("ProjectID " + projectId + "를 찾을 수 없습니다."));

        // 동일한 이름의 폴더가 있는지 중복 체크
        if (parentFolderId != null) {
            boolean folderExists = folderRepository.existsByNameAndParentFolderId(folderName, parentFolderId);
            if (folderExists) {
                throw new IllegalStateException("동일한 이름의 폴더가 이미 존재합니다.");
            }
        } else {
            boolean folderExists = folderRepository.existsByNameAndProjectIdAndParentFolderIsNull(folderName, projectId);
            if (folderExists) {
                throw new IllegalStateException("동일한 이름의 폴더가 이미 존재합니다.");
            }
        }

        Folder parentFolder = null;

        String folderPath;
        if (parentFolderId != null) {
            parentFolder = folderRepository.findById(parentFolderId).orElseThrow(
                    () -> new EntityNotFoundException("폴더 ID " + parentFolderId + " 를 찾을 수 없습니다."));
            folderPath = parentFolder.getPath() + "/" + folderName;
        } else {
            folderPath = "/projects/" + projectId + "/" + folderName;
        }

        //빌더 사용해서 객체 생성(setter사용X)
        Folder folder = Folder.builder()
                .name(folderName)
                .path(folderPath)
                .parentFolder(parentFolder)
                .project(project)
                .build();
        return folderRepository.save(folder);
    }

    /**
     * 프로젝트 아래 파일 생성
     * @param fileName 생성할 파일의 이름
     * @param content 생성할 파일의 내용
     * @param projectId 파일이 속한 프로젝트의 ID
     * @return 생성된 File 객체
     */
    @Transactional
    public File createFileInProject(String fileName, String content, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new RuntimeException("ProgectID " + projectId + "를 찾을 수 없습니다.")
        );

        if (fileRepository.existsByNameAndProjectId(fileName, projectId)) {
            throw new IllegalStateException("프로젝트에 동일한 이름의 파일이 이미 존재합니다.");
        }

        String filePath = "/projects/" + projectId + "/" + fileName;

        //빌더 사용해 File 객체 생성(setter사용X)
        File file = File.builder()
                .name(fileName)
                .content(content)
                .size(content.length())
                .path(filePath)
                .project(project)
                .build();

        return fileRepository.save(file);

    }

    /**
     * 파일 생성
     * @param fileName 생성할 파일의 이름
     * @param content 생성할 파일의 내용
     * @param folderId 파일이 속한 폴더의 ID
     * @param projectId 파일이 속한 프로젝트의 ID
     * @return 생성된 File 객체
     */
    @Transactional
    public File createFile(String fileName, String content, Long folderId, Long projectId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new RuntimeException("폴더 ID " + folderId + " 를 찾을 수 없습니다."));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("ProjectID " + projectId + "를 찾을 수 없습니다."));

        // 폴더 내에서 같은 이름의 파일이 있는지 중복 체크 (필요한 경우)
        if (fileRepository.existsByNameAndFolderId(fileName, folderId)) {
            throw new IllegalStateException("폴더에 동일한 이름의 파일이 이미 존재합니다.");
        }

        String filePath = folder.getPath() + "/" + fileName;

        //빌더 사용해 File 객체 생성(setter사용X)
        File file = File.builder()
                .name(fileName)
                .content(content)
                .size(content.length())
                .path(filePath)
                .folder(folder)
                .project(project)
                .build();

        return fileRepository.save(file);
    }

    /**
     * 프로젝트 내 최상위 폴더들을 조회
     * @param projectId 폴더들이 속한 프로젝트의 ID
     * @return 프로젝트 내 최상위 폴더들의 List
     */
    public List<Folder> getFoldersByProjectIdAndParentFolderIsNull(Long projectId) {
        return folderRepository.findByProjectIdAndParentFolderIsNull(projectId);
    }

    /**
     * 프로젝트 내 폴더에 속하지 않은 파일들을 조회
     * @param projectId 파일들이 속한 프로젝트의 ID
     * @return 프로젝트 내 폴더에 속하지 않은 파일들의 List
     */
    public List<File> getFilesByProjectIdAndNoFolder(Long projectId) {
        return fileRepository.findByProjectIdAndFolderIsNull(projectId);
    }

    /**
     * 폴더 내의 하위 폴더들을 조회
     * @param folderId 상위 폴더의 ID
     * @return 해당 상위 폴더에 속한 하위 폴더들의 List
     */
    public List<Folder> getSubFolderByFolderId(Long folderId) {
        return folderRepository.findByParentFolderId(folderId);
    }

    /**
     * 폴더 내의 파일들을 조회
     * @param folderId 폴더의 ID
     * @return 해당 폴더에 속한 파일들의 List
     */
    public List<File> getFilesByFolderId(Long folderId) {
        return fileRepository.findByFolderId(folderId);
    }

    /**
     * 폴더 수정
     * @param folderId 수정할 폴더의 ID
     * @param folderName 수정할 폴더의 이름
     * @param parentFolderId 부모 폴더의 ID (최상위 폴더 = null)
     * @param projectId 폴더가 속한 프로젝트의 ID
     * @return 수정된 Folder 객체
     */
    @Transactional
    public Folder updateFolder(Long folderId, String folderName, Long parentFolderId, Long projectId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new RuntimeException("폴더 ID " + folderId + " 를 찾을 수 없습니다."));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("ProjectID " + projectId + "를 찾을 수 없습니다."));

        String newFolderName = folderName != null ? folderName : folder.getName();
        Folder parentFolder = null;

        String newPath;

        if (parentFolderId != null) {
            parentFolder = folderRepository.findById(parentFolderId).orElseThrow(
                    () -> new RuntimeException("상위 폴더 ID " + parentFolderId + " 를 찾을 수 없습니다."));

            newPath = parentFolder.getPath() + "/" + folder.getName();
        } else {
            newPath = "/projects/" + projectId + "/" + folder.getName();
        }

        // Builder를 사용하여 폴더 객체를 새로운 값으로 생성
        Folder updateFolder = Folder.builder()
                .id(folder.getId())  // 기존 ID 유지
                .name(newFolderName)
                .path(newPath)
                .parentFolder(parentFolder)
                .project(project)
                .build();

        //하위 폴더,파일 경로 업데이트
        updateSubFoldersAndFilesPath(updateFolder, newPath);

        return folderRepository.save(updateFolder);
    }

    /**
     * 부모 폴더의 경로 변경시 하위 폴더, 파일 path 업데이트
     * @param parentFolder 경로가 변경된 부모 Folder 객체
     * @param parentPath 새로운 부모 폴더의 path
     */
    private void updateSubFoldersAndFilesPath(Folder parentFolder, String parentPath) {
        //하위 폴더들 경로 업데이트
        if (parentFolder.getSubFolders() != null) {
            parentFolder.getSubFolders().stream()
                    .filter(subFolder -> subFolder != null && !subFolder.equals(parentFolder.getParentFolder()))  // 부모 폴더와 동일하지 않은 하위 폴더 필터링
                    .forEach(subFolder -> {
                        String newSubFolderPath = parentPath + "/" + subFolder.getName();

                        Folder updatedSubFolder = Folder.builder()
                                .id(subFolder.getId())
                                .name(subFolder.getName())
                                .path(newSubFolderPath)
                                .parentFolder(parentFolder)  // 부모 폴더 업데이트
                                .subFolders(subFolder.getSubFolders())
                                .files(subFolder.getFiles())
                                .project(subFolder.getProject())
                                .build();

                        folderRepository.save(updatedSubFolder);  // 변경된 폴더 저장
                        updateSubFoldersAndFilesPath(updatedSubFolder, newSubFolderPath);  //재귀
                    });
        }


        //하위 파일들 경로 업데이트(Optional사용해서 널포인터에러방지. null일경우빈리스트반환)
        Optional.ofNullable(parentFolder.getFiles())
                .orElse(Collections.emptyList())
                .stream()
                .map(file -> {
                    String newFilePath = parentPath + "/" + file.getName();

                    //새로운 File 객체 생성
                    File updatedFile = File.builder()
                            .id(file.getId())
                            .name(file.getName())
                            .path(newFilePath)
                            .size(file.getSize())
                            .content(file.getContent())
                            .folder(parentFolder)  //파일의 폴더 업데이트
                            .project(file.getProject())
                            .build();

                    return fileRepository.save(updatedFile);    // 반환값을 명시적으로 사용
                });
    }

    /**
     * 파일 수정
     * @param fileId 수정할 파일의 ID
     * @param fileName 수정할 파일의 이름
     * @param content 수정할 파일의 내용
     * @param path 수정할 파일의 경로
     * @param folderId 파일이 속한 폴더의 ID
     * @param projectId 파일이 속한 프로젝트의 ID
     * @return 수정된 File 객체
     */
    @Transactional
    public File updateFile(Long fileId, String fileName, String content, String path, Long folderId, Long projectId) {
        File file = fileRepository.findById(fileId).orElseThrow(
                () -> new RuntimeException("파일 ID" + fileId + "를 찾을 수 없습니다.")
        );

        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new RuntimeException("폴더 ID " + folderId + " 를 찾을 수 없습니다."));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("ProjectID " + projectId + "를 찾을 수 없습니다."));

        String newFileName = fileName != null ? fileName : file.getName();
        String newContent = content != null ? content : file.getContent();
        long newSize = content != null ? content.length() : file.getSize();

        // 파일 경로 업데이트
        String newFilePath = folder.getPath() + "/" + newFileName;

        //빌더 사용해 새로운 File 객체 생성
        File updatedFile = File.builder()
                .id(file.getId())  // 기존 ID 유지
                .name(newFileName)
                .content(newContent)
                .size(newSize)
                .path(newFilePath)
                .folder(folder)
                .project(project)
                .build();

        return fileRepository.save(updatedFile);
    }

    /**
     * 폴더 삭제(하위 폴더, 파일 삭제)
     * @param folderId 삭제할 폴더의 ID
     */
    @Transactional
    public void deleteFolderWithContents(Long folderId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new RuntimeException("폴더를 찾을 수 없습니다.")
        );

        //파일 삭제(선언형)
        folder.getFiles().forEach(fileRepository::delete);

        //서브 폴더 삭제(선언형,재귀)
        folder.getSubFolders().forEach(subFolder -> deleteFolderWithContents(subFolder.getId()));

        folderRepository.delete(folder);
    }

    /**
     * 파일 삭제
     * @param fileId 삭제할 파일의 ID
     */
    @Transactional
    public void deleteFile(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(
                () -> new RuntimeException("파일을 찾을 수 없습니다.")
        );
        fileRepository.delete(file);
    }

}


