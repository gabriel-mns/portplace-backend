package com.pucpr.portplace.authentication.features.ahp.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class AHPNotFoundException extends EntityNotFoundException {

    public AHPNotFoundException(Long ahpId) {
        super("AHP not found with ID: " + ahpId);
    }

    public AHPNotFoundException(String message) {
        super(message);
    }
    
}
