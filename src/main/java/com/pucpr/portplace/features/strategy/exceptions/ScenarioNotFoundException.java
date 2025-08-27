package com.pucpr.portplace.features.strategy.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ScenarioNotFoundException extends EntityNotFoundException {
    
    public ScenarioNotFoundException(Long scenarioId) {
        super("Scenario could not be found with ID: " + scenarioId);
    }

    public ScenarioNotFoundException(String message) {
        super(message);
    }

}
