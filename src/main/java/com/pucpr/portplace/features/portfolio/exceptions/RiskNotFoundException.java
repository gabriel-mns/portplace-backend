package com.pucpr.portplace.features.portfolio.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class RiskNotFoundException extends EntityNotFoundException {

    public RiskNotFoundException(Long id) {
        super("Risk could not be found with ID " + id + ".");
    }
    
}
