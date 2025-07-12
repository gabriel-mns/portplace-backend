package com.pucpr.portplace.authentication.features.project.services;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.authentication.features.project.entities.Project;
import com.pucpr.portplace.authentication.features.project.mappers.ProjectMapper;
import com.pucpr.portplace.authentication.features.project.repositories.ProjectRepository;
import com.pucpr.portplace.authentication.features.user.entities.User;
import com.pucpr.portplace.authentication.features.user.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
    
    private ProjectRepository projectRepository;
    private UserService userService;
    private ProjectMapper projectMapper;

    // CREATE
    public ProjectReadDTO createProject(ProjectCreateDTO projectDTO) {

        //TODO: Treat the case when the project manager is not found (try catch)
        User projectManager = userService.getUserByIdEntity(projectDTO.getProjectManager());

        Project newProject = projectMapper.toEntity(projectDTO);
        newProject.setProjectManager(projectManager);

        Project savedProject = projectRepository.save(newProject);
        
        return projectMapper.toReadDTO(savedProject);

    }

    // UPDATE
    public ProjectReadDTO updateProject(ProjectUpdateDTO projectDTO, long projectId) {

        Project updatedProject = projectRepository.findById(projectId).get();

        projectMapper.updateFromDTO(projectDTO, updatedProject);

        Project savedProject = projectRepository.save(updatedProject);

        ProjectReadDTO projectReadDTO = projectMapper.toReadDTO(savedProject);

        return projectReadDTO;

    }

    // DELETE
    public void deleteProject(long projectId) {

        projectRepository.deleteById(projectId);

        // TODO: Treat the case when the project is not found

    }

    public void disableProject(long projectId) {

        Project project = projectRepository.findById(projectId).get();

        project.setDisabled(true);

        projectRepository.save(project);
        
        // TODO: Treat the case when the project is not found

    }

    // READ
    // TODO: Implement pagination, sorting and filtering methods
    public ProjectReadDTO getProjectById(long projectId) {

        Project project = projectRepository.findById(projectId).get();

        ProjectReadDTO projectDTO = projectMapper.toReadDTO(project);

        // TODO: Treat the case when the project is not found

        return projectDTO;
    
    }

    public Project getProjecEntitytById(long projectId) {

        return projectRepository.findById(projectId).get();
    
    }

    public ResponseEntity<List<ProjectReadDTO>> getAllProjects(boolean includeDisabled) {

        List<Project> projects;

        if(includeDisabled) {
            // If includes, get all projects
            projects = projectRepository.findAll();
        } else {
            // Else, get only not disabled (enabled) projects
            projects = projectRepository.findByDisabled(false);
        }

        List<ProjectReadDTO> projectsDTO = projectMapper.toReadDTO(projects);

        // TODO: Implement pagination
        // TODO: Implement sorting
        // TODO: Implement filtering

        return ResponseEntity.ok(projectsDTO);
    
    }

    public List<ProjectReadDTO> getAllProjectsByProjectManagerId(long projectManagerId, boolean includeDisabled) {

        List<Project> projects;

        if(includeDisabled) {
            
            // If includes, get all projects by project manager id
            projects = projectRepository.findByProjectManagerId(projectManagerId);
            
        } else {
            
            // If includes, get only enabled projects by project manager id
            projects = projectRepository.findByProjectManagerIdAndDisabled(projectManagerId, false);

        }

        List<ProjectReadDTO> projectsDTO = projectMapper.toReadDTO(projects);

        return projectsDTO;
   
    }
    
}
