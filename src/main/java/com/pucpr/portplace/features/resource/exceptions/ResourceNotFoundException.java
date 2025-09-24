package com.pucpr.portplace.features.resource.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ResourceNotFoundException extends EntityNotFoundException {
    
    public ResourceNotFoundException(Long resourceId) {
        super("Resource could not be found with ID: " + resourceId);
    }
    
}
