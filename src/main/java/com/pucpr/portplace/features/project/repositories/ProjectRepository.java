package com.pucpr.portplace.features.project.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.project.enums.ProjectStatusEnum;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    @Query("""
        SELECT p
        FROM Project p
        WHERE (:projectManagerId IS NULL OR p.projectManager.id = :projectManagerId)
          AND (:includeDisabled = true OR p.disabled = false)
          AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:status IS NULL OR p.status IN :status)
    """)
    Page<Project> findByFilters(
        @Param("projectManagerId") Long projectManagerId,
        @Param("name") String name,
        @Param("status") List<ProjectStatusEnum> status,
        @Param("includeDisabled") boolean includeDisabled,
        Pageable pageable
    );

}
