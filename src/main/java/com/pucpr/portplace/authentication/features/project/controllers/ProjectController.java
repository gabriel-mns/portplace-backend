package com.pucpr.portplace.authentication.features.project.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pucpr.portplace.authentication.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.authentication.features.project.services.ProjectService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    
    private ProjectService projectService;

    // CREATE
    @PostMapping
    public ResponseEntity<ProjectReadDTO> createProject(@RequestBody ProjectCreateDTO projectDTO) {
        
        ProjectReadDTO createdProject = projectService.createProject(projectDTO);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdProject.getId())
            .toUri();
        
        return ResponseEntity.created(uri).body(createdProject);
        
    }

    // UPDATE
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectReadDTO> updateProject(
        @PathVariable long projectId,
        @RequestBody ProjectUpdateDTO projectDTO
    ) {
        ProjectReadDTO updatedProject = projectService.updateProject(projectDTO, projectId);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(updatedProject.getId())
            .toUri();

        return ResponseEntity.ok()
            .location(uri)
            .body(updatedProject);
    }

    // DELETE
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> disableProject(@PathVariable long projectId) {
        
        projectService.disableProject(projectId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{projectId}/hard-delete")
    public ResponseEntity<Void> deleteProject(@PathVariable long projectId) {

        projectService.deleteProject(projectId);

        return ResponseEntity.noContent().build();

    }

    // READ
    // TODO: Implement pagination, filtering and sorting
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectReadDTO> getProjectById(@PathVariable long projectId) {

        ProjectReadDTO projectDTO = projectService.getProjectById(projectId);

        return ResponseEntity.ok()
            .body(projectDTO);

    }

    @GetMapping
    public ResponseEntity<List<ProjectReadDTO>> getAllProjects(

        @RequestParam(defaultValue = "false") boolean includeDisabled

    ) {

        return projectService.getAllProjects(includeDisabled);

    }

    @GetMapping("/manager/{projectManagerId}")
    public ResponseEntity<List<ProjectReadDTO>> getProjectsByManager(

        @PathVariable long projectManagerId,
        @RequestParam(defaultValue = "false") boolean includeDisabled

        ) {

        List<ProjectReadDTO> projects = projectService.getAllProjectsByProjectManagerId(projectManagerId, includeDisabled);

        return ResponseEntity.ok()
            .body(projects);

    }

}
