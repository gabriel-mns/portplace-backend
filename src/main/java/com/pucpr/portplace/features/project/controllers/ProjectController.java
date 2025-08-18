package com.pucpr.portplace.features.project.controllers;

import java.net.URI;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.pucpr.portplace.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.features.project.services.ProjectService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Project", description = "Related to the Project CRUD operations")
@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    
    private ProjectService projectService;

    // CREATE
    @PostMapping
    public ResponseEntity<ProjectReadDTO> createProject(@RequestBody @Valid ProjectCreateDTO projectDTO) {
        
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
        @RequestBody @Valid ProjectUpdateDTO projectDTO
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
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectReadDTO> getProjectById(@PathVariable long projectId) {

        ProjectReadDTO projectDTO = projectService.getProjectById(projectId);

        return ResponseEntity.ok()
            .body(projectDTO);

    }

    // TODO: Implement pagination, filtering and sorting
    @GetMapping
    public ResponseEntity<Page<ProjectReadDTO>> getAllProjects(
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return projectService.getAllProjects(pageable, includeDisabled);

    }

    @GetMapping("/manager/{projectManagerId}")
    public ResponseEntity<Page<ProjectReadDTO>> getProjectsByManager(
        @PathVariable long projectManagerId,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ProjectReadDTO> projects = projectService.getAllProjectsByProjectManagerId(projectManagerId, includeDisabled, pageable);

        return ResponseEntity.ok()
            .body(projects);

    }

}
