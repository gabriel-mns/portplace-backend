package com.pucpr.portplace.features.strategy.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class ScenarioAlreadyAuthorizedException extends BusinessException {
    
    public ScenarioAlreadyAuthorizedException(Long scenarioId) {
        super("Scenario with ID: " + scenarioId + " is already authorized and cannot be modified.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ScenarioAlreadyAuthorizedException(String message) {
        super(message);
    }
    
}
