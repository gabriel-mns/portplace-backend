package com.pucpr.portplace.features.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.services.internal.EvaluationEntityService;
import com.pucpr.portplace.features.project.dtos.ProjectCancelationPatchDTO;
import com.pucpr.portplace.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.features.project.mappers.ProjectMapper;
import com.pucpr.portplace.features.project.repositories.ProjectRepository;
import com.pucpr.portplace.features.project.services.validations.ProjectValidationService;
import com.pucpr.portplace.features.strategy.services.internal.StrategicObjectiveEntityService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
    
    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;
    private ProjectValidationService validationService;
    private StrategicObjectiveEntityService strategicObjectiveService;
    private EvaluationEntityService evaluationService;

    // CREATE
    public ProjectReadDTO createProject(@Valid ProjectCreateDTO projectDTO) {

        Project newProject = projectMapper.toEntity(projectDTO);
        // newProject.updateCalculatedValues();

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

    }

    // READ
    public ProjectReadDTO getProjectById(long projectId) {

        validationService.validateBeforeGet(projectId);

        Project project = projectRepository.findById(projectId).get();

        ProjectReadDTO projectDTO = projectMapper.toReadDTO(project);

        projectDTO.setStrategicObjectives(strategicObjectiveService.findObjectivesByProjectId(projectId));
        projectDTO.setEvaluations(evaluationService.findEvaluationsByProjectId(projectId));

        return projectDTO;
    
    }

    public ResponseEntity<Page<ProjectReadDTO>> getAllProjects(
        Long portfolioId,
        List<ProjectStatusEnum> status,
        String projectName,
        Pageable pageable,
        boolean includeDisabled
    ) {

        validationService.validateBeforeGetAll(portfolioId);

        Page<Project> projects = projectRepository.findByFilters(
            portfolioId,
            projectName,
            status,
            includeDisabled,
            pageable
        );

        Page<ProjectReadDTO> projectsDTO = projects.map(projectMapper::toReadDTO);

        return ResponseEntity.ok(projectsDTO);
    
    }

    public List<ProjectReadDTO> getAllProjectsUnpaged(
        Long portfolioId,
        List<ProjectStatusEnum> status
    ) {

        validationService.validateBeforeGetAll(portfolioId);

        List<Project> projects = projectRepository.findByFilters(
            portfolioId,
            status
        );

        List<ProjectReadDTO> projectsDTO = projects.stream()
            .map(projectMapper::toReadDTO)
            .collect(Collectors.toList());

        return projectsDTO;

    }

    public ProjectReadDTO cancelProject(
        long projectID,
        ProjectCancelationPatchDTO cancelationReasonDTO
    ) {

        validationService.validateBeforeCancel(projectID);

        Project project = projectRepository.findById(projectID).get();

        project.setCancellationReason(cancelationReasonDTO.getCancellationReason());
        project.setStatus(ProjectStatusEnum.CANCELLED);

        projectRepository.save(project);
        
        return projectMapper.toReadDTO(project);

    }

    // public Page<ProjectReadDTO> getAllProjectsByProjectManagerId(
    //     long projectManagerId,
    //     List<ProjectStatusEnum> status,
    //     String projectName,
    //     boolean includeDisabled, 
    //     Pageable pageable
    // ) {

    //     validationService.validateBeforeGetByProjectManagerId(projectManagerId);

    //     Page<Project> projects = projectRepository.findByFilters(
    //         projectManagerId,
    //         projectName,
    //         status,
    //         includeDisabled,
    //         pageable
    //     );

    //     Page<ProjectReadDTO> projectsDTO = projects.map(projectMapper::toReadDTO);

    //     return projectsDTO;
   
    // }
    
}
