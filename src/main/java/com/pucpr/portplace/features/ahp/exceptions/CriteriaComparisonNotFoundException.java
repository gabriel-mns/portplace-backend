package com.pucpr.portplace.features.ahp.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CriteriaComparisonNotFoundException extends EntityNotFoundException {

    public CriteriaComparisonNotFoundException(Long criteriaComparisonId) {
        super("Criteria comparison not found with ID: " + criteriaComparisonId);
    }

    public CriteriaComparisonNotFoundException(String message) {
        super(message);
    }
    
}
