package com.pucpr.portplace.features.project.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class EvmEntryNotFoundException extends EntityNotFoundException {
    
    public EvmEntryNotFoundException(Long id) {
        super("EVM could not be found with ID: " + id);
    }
    
}
