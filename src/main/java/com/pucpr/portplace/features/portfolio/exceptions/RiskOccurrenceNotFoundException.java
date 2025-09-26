package com.pucpr.portplace.features.portfolio.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class RiskOccurrenceNotFoundException extends EntityNotFoundException {

    public RiskOccurrenceNotFoundException(Long id) {
        super("Risk occurrence could not be found with ID " + id + ".");
    }
    
}
