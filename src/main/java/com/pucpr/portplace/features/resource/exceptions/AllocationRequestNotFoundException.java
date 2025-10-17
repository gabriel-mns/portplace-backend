package com.pucpr.portplace.features.resource.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class AllocationRequestNotFoundException extends EntityNotFoundException {
    
    public AllocationRequestNotFoundException(Long id) {
        super("Allocation Request could not be found with id " + id + ".");
    }
    
}
