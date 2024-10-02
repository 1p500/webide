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

import java.util.List;

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

    /////////////////////////폴더 생성
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

        Folder folder = new Folder();
        folder.setName(folderName);
        folder.setProject(project);

        String folderPath;
        if (parentFolderId != null) {
            Folder parentFolder = folderRepository.findById(parentFolderId).orElseThrow(
                    () -> new EntityNotFoundException("폴더 ID " + parentFolderId + " 를 찾을 수 없습니다."));
            folder.setParentFolder(parentFolder);
            folderPath = parentFolder.getPath() + "/" + folderName;
        } else {
            folderPath = "/projects/" + projectId + "/" + folderName;
            folder.setParentFolder(null);
        }
        folder.setPath(folderPath);

        return folderRepository.save(folder);
    }

    /////////////////////////프로젝트 아래 파일 생성
    @Transactional
    public File createFileInProject(String fileName, String content, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new RuntimeException("ProgectID " + projectId + "를 찾을 수 없습니다.")
        );

        if (fileRepository.existsByNameAndProjectId(fileName, projectId)) {
            throw new IllegalStateException("프로젝트에 동일한 이름의 파일이 이미 존재합니다.");
        }

        File file = new File();
        file.setName(fileName);
        file.setContent(content);
        file.setSize(content.length());
        file.setProject(project);

        String filePath = "/projects/" + projectId + "/" + fileName;
        file.setPath(filePath);

        return fileRepository.save(file);

    }


    /////////////////////////파일 생성
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

        File file = new File();
        file.setName(fileName);
        file.setContent(content);
        file.setSize(content.length());
        file.setFolder(folder);
        file.setProject(project);

        String filePath = folder.getPath() + "/" + fileName;
        file.setPath(filePath);

        return fileRepository.save(file);
    }


    /////////////////////////폴더, 파일 조회
    public List<Folder> getFoldersByProjectIdAndParentFolderIsNull(Long projectId) {
        return folderRepository.findByProjectIdAndParentFolderIsNull(projectId);
    }

    public List<File> getFilesByProjectIdAndNoFolder(Long projectId) {
        return fileRepository.findByProjectIdAndFolderIsNull(projectId);
    }

    public List<Folder> getSubFolderByFolderId(Long folderId) {
        return folderRepository.findByParentFolderId(folderId);
    }

    public List<File> getFilesByFolderId(Long folderId) {
        return fileRepository.findByFolderId(folderId);
    }


    /////////////////////////폴더 수정
    @Transactional
    public Folder patchFolder(Long folderId, String folderName, Long parentFolderId, Long projectId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new RuntimeException("폴더 ID " + folderId + " 를 찾을 수 없습니다."));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("ProjectID " + projectId + "를 찾을 수 없습니다."));

        String newFolderName = folderName != null ? folderName : folder.getName();

        String newPath;

        if (parentFolderId != null) {
            Folder parentFolder = folderRepository.findById(parentFolderId).orElseThrow(
                    () -> new RuntimeException("상위 폴더 ID " + parentFolderId + " 를 찾을 수 없습니다."));
            System.out.println("parent folder found" + parentFolder.getName());
            folder.setParentFolder(parentFolder);

            newPath = parentFolder.getPath() + "/" + folder.getName();
        } else {
            System.out.println("no parent folder");
            newPath = "/projects/" + projectId + "/" + folder.getName();
            folder.setParentFolder(null);
        }

        folder.setPath(newPath);
        System.out.println("new path: " + newPath);

        //하위 폴더,파일 경로 업데이트
        updateSebFoldersAndFilesPath(folder, newPath);

        return folderRepository.save(folder);
    }


    /////////////////////////하위 폴더,파일 경로 업데이트하는 메서드
    private void updateSebFoldersAndFilesPath(Folder parentFolder, String parentPath) {
        //하위 폴더들 경로 업데이트
        List<Folder> subFolders = parentFolder.getSubFolders();
        for (Folder subFolder : subFolders) {
            if (!subFolder.equals(parentFolder.getParentFolder())) {
                String newSubFolderPath = parentPath + "/" + subFolder.getName();
                subFolder.setPath(newSubFolderPath);
                updateSebFoldersAndFilesPath(subFolder, newSubFolderPath);  //재귀
            }
        }

        //하위 파일들 경로 업데이트
        List<File> files = parentFolder.getFiles();
        for (File file : files) {
            String newFilePath = parentPath + "/" + file.getName();
            file.setPath(newFilePath);
            fileRepository.save(file);
        }
    }


    /////////////////////////파일 수정
    @Transactional
    public File patchFile(Long fileId, String fileName, String content, Long folderId, Long projectId) {
        File file = fileRepository.findById(fileId).orElseThrow(
                () -> new RuntimeException("파일 ID" + fileId + "를 찾을 수 없습니다.")
        );

        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new RuntimeException("폴더 ID " + folderId + " 를 찾을 수 없습니다."));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("ProjectID " + projectId + "를 찾을 수 없습니다."));

        if (fileName != null) {
            file.setName(fileName);
            file.setPath(folder.getPath() + "/" + fileName);
        }

        if (content != null) {
            file.setContent(content);
            file.setSize(content.length());
        }

        return fileRepository.save(file);
    }


    /////////////////////////폴더 삭제
    @Transactional
    public void deleteFolderWithContents(Long folderId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new RuntimeException("폴더를 찾을 수 없습니다.")
        );

        List<File> files = folder.getFiles();
        for (File file : files) {
            fileRepository.delete(file);
        }

        List<Folder> subfolders = folder.getSubFolders();
        for (Folder subFolder : subfolders) {
                deleteFolderWithContents(subFolder.getId());    //재귀
        }
        folderRepository.delete(folder);
    }


    /////////////////////////파일 삭제
    @Transactional
    public void deleteFile(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(
                () -> new RuntimeException("파일을 찾을 수 없습니다.")
        );
        fileRepository.delete(file);
    }

}


