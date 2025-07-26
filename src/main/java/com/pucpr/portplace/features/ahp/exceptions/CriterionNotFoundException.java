package com.pucpr.portplace.features.ahp.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CriterionNotFoundException extends EntityNotFoundException {

    public CriterionNotFoundException(Long criterionId) {
        super("Criterion not found with ID: " + criterionId);
    }

    public CriterionNotFoundException(String message) {
        super(message);
    }
    
}
