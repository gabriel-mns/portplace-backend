package com.pucpr.portplace.features.project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.project.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    // TODO: Implement paginated methods

    Page<Project> findByProjectManagerId(long projectManagerId, Pageable pageable);

    Page<Project> findByProjectManagerIdAndDisabled(long projectManagerId, boolean disabled, Pageable pageable);

    Page<Project> findByDisabled(Pageable pageable, boolean disabled);

}
