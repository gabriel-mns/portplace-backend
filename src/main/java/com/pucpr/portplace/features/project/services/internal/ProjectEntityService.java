package com.pucpr.portplace.features.project.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.project.repositories.ProjectRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectEntityService {
    
    private final ProjectRepository projectRepository;

    public Project getProjectEntityById(Long id) {
        return projectRepository.findById(id).get();
    }

    public boolean existsById(Long projectId) {
        return projectRepository.existsById(projectId);
    }

}
