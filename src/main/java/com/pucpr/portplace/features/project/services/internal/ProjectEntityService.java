package com.pucpr.portplace.features.project.services.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.project.mappers.ProjectMapper;
import com.pucpr.portplace.features.project.repositories.ProjectRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectEntityService {
    
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public Project getProjectEntityById(Long id) {
        return projectRepository.findById(id).get();
    }

    public boolean existsById(Long projectId) {
        return projectRepository.existsById(projectId);
    }

    public void saveProjectEntity(Project project) {
        projectRepository.save(project);
    }

    public Page<ProjectReadDTO> findByStrategicObjectiveId(long objectiveId, String searchQuery, PageRequest pageable) {

        Page<Project> projects = projectRepository.findByObjectiveId(
            objectiveId,
            searchQuery,
            pageable
        );

        return projects.map(projectMapper :: toReadDTO);
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

}
