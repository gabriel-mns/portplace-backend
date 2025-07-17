package com.pucpr.portplace.authentication.features.ahp.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CriteriaGroupNotFoundException extends EntityNotFoundException {

    public CriteriaGroupNotFoundException(Long criteriaGroupId) {
        super("Criteria group not found with ID: " + criteriaGroupId);
    }

    public CriteriaGroupNotFoundException(String message) {
        super(message);
    }
    
}
