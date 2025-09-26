package com.pucpr.portplace.features.portfolio.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class StakeholderNotFoundException extends EntityNotFoundException {
    
    public StakeholderNotFoundException(Long id) {
        super("Stakeholder could not be found with ID: " + id);
    }
    
}
