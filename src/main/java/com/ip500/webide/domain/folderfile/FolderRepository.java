package com.ip500.webide.domain.folderfile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByProjectId(Long projectId);

    List<Folder> findByProjectIdAndParentFolderIsNull(Long projectId);
    List<Folder> findByParentFolderId(Long folderId);

    //동일한 이름의 폴더가 부모 폴더 내에 존재하는지 확인
    boolean existsByNameAndParentFolderId(String name, Long parentFolderId);

    //동일한 이름의 폴더가 프로젝트 내에 존재하는지 확인
    boolean existsByNameAndProjectIdAndParentFolderIsNull(String name, Long projectId);
}
