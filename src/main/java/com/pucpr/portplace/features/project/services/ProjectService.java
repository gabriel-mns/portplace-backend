package com.pucpr.portplace.features.project.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.project.mappers.ProjectMapper;
import com.pucpr.portplace.features.project.repositories.ProjectRepository;
import com.pucpr.portplace.features.project.services.validations.ProjectValidationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
    
    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;
    private ProjectValidationService validationService;

    // CREATE
    public ProjectReadDTO createProject(@Valid ProjectCreateDTO projectDTO) {

        validationService.validateBeforeCreate(projectDTO);

        Project newProject = projectMapper.toEntity(projectDTO);

        Project savedProject = projectRepository.save(newProject);
        
        return projectMapper.toReadDTO(savedProject);

    }

    // UPDATE
    public ProjectReadDTO updateProject(ProjectUpdateDTO projectDTO, long projectId) {

        validationService.validateBeforeUpdate(projectId, projectDTO);

        Project updatedProject = projectRepository.findById(projectId).get();

        projectMapper.updateFromDTO(projectDTO, updatedProject);

        Project savedProject = projectRepository.save(updatedProject);

        ProjectReadDTO projectReadDTO = projectMapper.toReadDTO(savedProject);

        return projectReadDTO;

    }

    // DELETE
    public void deleteProject(long projectId) {

        validationService.validateBeforeDelete(projectId);

        projectRepository.deleteById(projectId);

    }

    public void disableProject(long projectId) {

        validationService.validateBeforeDisable(projectId);

        Project project = projectRepository.findById(projectId).get();

        project.setDisabled(true);

        projectRepository.save(project);
        
        // TODO: Treat the case when the project is not found

    }

    // READ
    // TODO: Implement pagination, sorting and filtering methods
    public ProjectReadDTO getProjectById(long projectId) {

        validationService.validateBeforeGet(projectId);

        Project project = projectRepository.findById(projectId).get();

        ProjectReadDTO projectDTO = projectMapper.toReadDTO(project);

        // TODO: Treat the case when the project is not found

        return projectDTO;
    
    }

    public ResponseEntity<Page<ProjectReadDTO>> getAllProjects(
        Pageable pageable,
        boolean includeDisabled
    ) {

        Page<Project> projects;

        if(includeDisabled) {
            // If includes, get all projects
            projects = projectRepository.findAll(pageable);
        } else {
            // Else, get only not disabled (enabled) projects
            projects = projectRepository.findByDisabled(pageable, false);
        }

        // List<ProjectReadDTO> projectsDTO = projectMapper.toReadDTO(projects);
        Page<ProjectReadDTO> projectsDTO = projects.map(projectMapper::toReadDTO);

        // TODO: Implement filtering

        return ResponseEntity.ok(projectsDTO);
    
    }

    public Page<ProjectReadDTO> getAllProjectsByProjectManagerId(long projectManagerId, boolean includeDisabled, Pageable pageable) {

        validationService.validateBeforeGetByProjectManagerId(projectManagerId);

        Page<Project> projects;

        if(includeDisabled) {
            // If includes, get all projects by project manager id
            projects = projectRepository.findByProjectManagerId(projectManagerId, pageable);
        } else {
            // If includes, get only enabled projects by project manager id
            projects = projectRepository.findByProjectManagerIdAndDisabled(projectManagerId, false, pageable);
        }

        Page<ProjectReadDTO> projectsDTO = projects.map(projectMapper::toReadDTO);

        return projectsDTO;
   
    }
    
}
