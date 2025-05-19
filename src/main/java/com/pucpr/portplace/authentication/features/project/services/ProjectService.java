package com.pucpr.portplace.authentication.features.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.authentication.features.project.entities.Project;
import com.pucpr.portplace.authentication.features.project.repositories.ProjectRepository;
import com.pucpr.portplace.authentication.features.user.entities.User;
import com.pucpr.portplace.authentication.features.user.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
    
    private ProjectRepository projectRepository;
    private UserService userService;

    // CREATE
    public ResponseEntity<Void> createProject(ProjectCreateDTO projectDTO) {

        //TODO: Treat the case when the project manager is not found (try catch)
        User projectManager = userService.getUserByIdEntity(projectDTO.getProjectManager());


        // TODO: Create mapper to convert DTO to entity and vice versa
        Project newProject = new Project(
            projectDTO.getName(),
            projectDTO.getDescription(),
            projectDTO.getStatus(),
            projectDTO.getEarnedValue(),
            projectDTO.getPlannedValue(),
            projectDTO.getActualCost(),
            projectDTO.getBudget(),
            projectDTO.getPayback(),
            projectDTO.getStartDate(),
            projectDTO.getEndDate(),
            projectManager
        );

        projectRepository.save(newProject);

        return ResponseEntity.status(201).build();

    }

    // UPDATE
    public ResponseEntity<Void> updateProject(ProjectUpdateDTO projectDTO, long projectId) {

        //TODO: Treat the case when the project manager is not found (try catch)
        User projectManager = userService.getUserByIdEntity(projectDTO.getProjectManager());

        Project updatedProject = projectRepository.findById(projectId).get();

        updatedProject.setName(projectDTO.getName());
        updatedProject.setDescription(projectDTO.getDescription());
        updatedProject.setStatus(projectDTO.getStatus());
        updatedProject.setEarnedValue(projectDTO.getEarnedValue());
        updatedProject.setPlannedValue(projectDTO.getPlannedValue());
        updatedProject.setActualCost(projectDTO.getActualCost());
        updatedProject.setBudget(projectDTO.getBudget());
        updatedProject.setPayback(projectDTO.getPayback());
        updatedProject.setStartDate(projectDTO.getStartDate());
        updatedProject.setEndDate(projectDTO.getEndDate());
        updatedProject.setProjectManager(projectManager);
        
        // TODO: Create mapper to convert DTO to entity and vice versa

        projectRepository.save(updatedProject);

        return ResponseEntity.status(201).build();

    }

    // DELETE
    public ResponseEntity<Void> deleteProject(long projectId) {

        projectRepository.deleteById(projectId);

        // TODO: Treat the case when the project is not found

        return ResponseEntity.status(204).build();

    }

    // READ
    // TODO: Implement pagination, sorting and filtering methods
    public ResponseEntity<ProjectReadDTO> getProjectById(long projectId) {

        Project project = projectRepository.findById(projectId).get();

        ProjectReadDTO projectDTO = new ProjectReadDTO(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getStatus(),
            project.getEarnedValue(),
            project.getPlannedValue(),
            project.getActualCost(),
            project.getBudget(),
            project.getPayback(),
            project.getStartDate(),
            project.getEndDate()
            // project.getProjectManager().getId()
        );

        // TODO: Treat the case when the project is not found

        return ResponseEntity.ok(projectDTO);
    
    }

    public ResponseEntity<List<ProjectReadDTO>> getAllProjects() {

        List<Project> projects = projectRepository.findAll();

        List<ProjectReadDTO> projectsDTO = projects.stream()
            .map(project -> new ProjectReadDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                project.getEarnedValue(),
                project.getPlannedValue(),
                project.getActualCost(),
                project.getBudget(),
                project.getPayback(),
                project.getStartDate(),
                project.getEndDate()
                // project.getProjectManager().getId()
            ))
            .collect(Collectors.toList());

        // TODO: Implement a mapper to convert the list of projects to a list of DTOs
        // TODO: Implement pagination
        // TODO: Implement sorting
        // TODO: Implement filtering

        return ResponseEntity.ok(projectsDTO);
    
    }

    public ResponseEntity<List<ProjectReadDTO>> getAllProjectsByProjectManagerId(long projectManagerId){

        List<Project> projects = projectRepository.findByProjectManagerId(projectManagerId);

        List<ProjectReadDTO> projectsDTO = projects.stream()
            .map(project -> new ProjectReadDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                project.getEarnedValue(),
                project.getPlannedValue(),
                project.getActualCost(),
                project.getBudget(),
                project.getPayback(),
                project.getStartDate(),
                project.getEndDate()
                // project.getProjectManager().getId()
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(projectsDTO);
   
    }

}
