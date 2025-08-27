package com.pucpr.portplace.features.strategy.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.exceptions.ScenarioNotFoundException;
import com.pucpr.portplace.features.strategy.exceptions.ScenarioRankingNotFoundException;
import com.pucpr.portplace.features.strategy.specs.ScenarioExistsSpecification;
import com.pucpr.portplace.features.strategy.specs.ScenarioRankingExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScenarioRankingValidationService {
    
    private ScenarioExistsSpecification scenarioExistsSpecification;
    private ScenarioRankingExistsSpecification scenarioRankingExistsSpecification;

    public void validateBeforeUpdate(long scenarioId, long rankingId) {
        
        if(!scenarioExistsSpecification.isSatisfiedBy(scenarioId)) {
            throw new ScenarioNotFoundException(scenarioId);
        }
        
        if(!scenarioRankingExistsSpecification.isSatisfiedBy(rankingId)) {
            throw new ScenarioRankingNotFoundException(rankingId);
        }

    }

    public void validateBeforeGetAll(long scenarioId) {
        
        if(!scenarioExistsSpecification.isSatisfiedBy(scenarioId)) {
            throw new ScenarioNotFoundException(scenarioId);
        }

    }

}
