package com.pucpr.portplace.authentication.features.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.project.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    // TODO: Implement paginated methods
    
    List<Project> findByProjectManagerId(long projectManagerId);

}
