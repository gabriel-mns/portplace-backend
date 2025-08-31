package com.pucpr.portplace.features.strategy.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ScenarioRankingNotFoundException extends EntityNotFoundException {
    
    public ScenarioRankingNotFoundException(Long rankingId) {
        super("Scenario Ranking could not be found with ID: " + rankingId);
    }

    public ScenarioRankingNotFoundException(String message) {
        super(message);
    }
    
}
