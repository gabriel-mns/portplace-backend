package com.pucpr.portplace.authentication.features.project.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ProjectNotFoundException extends EntityNotFoundException{
    
    public ProjectNotFoundException(Long projectId) {
        super("Project not found with ID: " + projectId);
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }

}
