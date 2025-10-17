package com.pucpr.portplace.features.resource.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class PositionNotFoundException extends EntityNotFoundException {
    
    public PositionNotFoundException(Long positionId) {
        super("Position could not be found with ID: " + positionId);
    }

}
