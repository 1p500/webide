package com.ip500.webide.domain.folderfile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByProjectId(Long projectId);

    List<File> findByFolderId(Long folderId);
    List<File> findByProjectIdAndFolderIsNull(Long projectId);

    //파일이 특정 폴더 내에 같은 이름으로 존재하는지 확인
    boolean existsByNameAndFolderId(String name, Long folderId);
    boolean existsByNameAndProjectId(String name, Long projectId);

}
