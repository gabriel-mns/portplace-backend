package com.pucpr.portplace.features.resource.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class AllocationNotFoundException extends EntityNotFoundException {
    
    public AllocationNotFoundException(Long id) {
        super("Allocation could not be found with id " + id + ".");
    }
    
}
