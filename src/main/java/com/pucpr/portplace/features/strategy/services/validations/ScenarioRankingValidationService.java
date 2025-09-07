package com.pucpr.portplace.features.strategy.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.entities.Scenario;
import com.pucpr.portplace.features.strategy.exceptions.PortfolioAlreadyCompletedException;
import com.pucpr.portplace.features.strategy.exceptions.ScenarioAlreadyAuthorizedException;
import com.pucpr.portplace.features.strategy.exceptions.ScenarioNotFoundException;
import com.pucpr.portplace.features.strategy.exceptions.ScenarioRankingNotFoundException;
import com.pucpr.portplace.features.strategy.services.internal.ScenarioEntityService;
import com.pucpr.portplace.features.strategy.specs.PortfolioIsNotCompletedSpecification;
import com.pucpr.portplace.features.strategy.specs.ScenarioExistsSpecification;
import com.pucpr.portplace.features.strategy.specs.ScenarioIsNotAuthorizedSpecification;
import com.pucpr.portplace.features.strategy.specs.ScenarioRankingExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScenarioRankingValidationService {
    
    private ScenarioEntityService scenarioEntityService;

    private ScenarioExistsSpecification scenarioExistsSpecification;
    private ScenarioRankingExistsSpecification scenarioRankingExistsSpecification;
    private ScenarioIsNotAuthorizedSpecification scenarioIsNotAuthorizedSpecification;
    private PortfolioIsNotCompletedSpecification portfolioIsNotCompletedSpecification;

    public void validateBeforeUpdate(long scenarioId, long rankingId) {
        
        if(!scenarioExistsSpecification.isSatisfiedBy(scenarioId)) {
            throw new ScenarioNotFoundException(scenarioId);
        }
        
        if(!scenarioRankingExistsSpecification.isSatisfiedBy(rankingId)) {
            throw new ScenarioRankingNotFoundException(rankingId);
        }

        if(!scenarioIsNotAuthorizedSpecification.isSatisfiedBy(scenarioId)) {
            throw new ScenarioAlreadyAuthorizedException(scenarioId);
        }

        Scenario scenario = scenarioEntityService.getScenarioById(scenarioId);

        if(!portfolioIsNotCompletedSpecification.isSatisfiedBy(scenario.getPortfolio().getId())) {
            throw new PortfolioAlreadyCompletedException(scenario.getPortfolio().getId());
        }

    }

    public void validateBeforeGetAll(long scenarioId) {
        
        if(!scenarioExistsSpecification.isSatisfiedBy(scenarioId)) {
            throw new ScenarioNotFoundException(scenarioId);
        }

    }

}
