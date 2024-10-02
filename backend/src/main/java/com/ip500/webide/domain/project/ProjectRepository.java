package com.ip500.webide.domain.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByOwnerIdAndName(Long ownerId, String name);
    List<Project> findByOwnerId(Long ownerId);
}
