package com.pucpr.portplace.features.strategy.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class StrategicObjectiveNotFoundException extends EntityNotFoundException {
    
    public StrategicObjectiveNotFoundException(Long strategicObjectiveId) {
        super("Strategic Objective could not be found with ID: " + strategicObjectiveId);
    }

    public StrategicObjectiveNotFoundException(String message) {
        super(message);
    }

}
