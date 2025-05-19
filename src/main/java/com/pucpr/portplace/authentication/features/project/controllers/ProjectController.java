package com.pucpr.portplace.authentication.features.project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pucpr.portplace.authentication.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectUpdateDTO;
import com.pucpr.portplace.authentication.features.project.services.ProjectService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/projects", produces = "application/json")
@AllArgsConstructor
public class ProjectController {
    
    private ProjectService projectService;

    // CREATE
    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody ProjectCreateDTO projectDTO) {
        return projectService.createProject(projectDTO);
    }

    // UPDATE
    @PutMapping("/{projectId}")
    public ResponseEntity<Void> updateProject(@PathVariable long projectId,
                                              @RequestBody ProjectUpdateDTO projectDTO) {
        return projectService.updateProject(projectDTO, projectId);
    }

    // DELETE
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable long projectId) {
        return projectService.deleteProject(projectId);
    }

    // READ
    // TODO: Implement pagination, filtering and sorting
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectReadDTO> getProjectById(@PathVariable long projectId) {
        return projectService.getProjectById(projectId);
    }

    @GetMapping
    public ResponseEntity<List<ProjectReadDTO>> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/manager/{projectManagerId}")
    public ResponseEntity<List<ProjectReadDTO>> getProjectsByManager(@PathVariable long projectManagerId) {
        return projectService.getAllProjectsByProjectManagerId(projectManagerId);
    }

}
