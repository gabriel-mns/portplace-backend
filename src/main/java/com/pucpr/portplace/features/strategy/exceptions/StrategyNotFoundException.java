package com.pucpr.portplace.features.strategy.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class StrategyNotFoundException extends EntityNotFoundException {
    
    public StrategyNotFoundException(Long strategyId) {
        super("Strategy not found with ID: " + strategyId);
    }

    public StrategyNotFoundException(String message) {
        super(message);
    }

}
