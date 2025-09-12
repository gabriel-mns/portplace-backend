package com.pucpr.portplace.features.strategy.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.exceptions.EvaluationGroupNotFoundException;
import com.pucpr.portplace.features.ahp.specs.EvaluationGroupExistsSpecification;
import com.pucpr.portplace.features.portfolio.exceptions.PortfolioNotFoundException;
import com.pucpr.portplace.features.portfolio.specs.PortfolioExistsSpecification;
import com.pucpr.portplace.features.strategy.entities.Scenario;
import com.pucpr.portplace.features.strategy.exceptions.PortfolioAlreadyCompletedException;
import com.pucpr.portplace.features.strategy.exceptions.ScenarioAlreadyAuthorizedException;
import com.pucpr.portplace.features.strategy.exceptions.ScenarioNotFoundException;
import com.pucpr.portplace.features.strategy.exceptions.StrategyNotFoundException;
import com.pucpr.portplace.features.strategy.exceptions.UncategorizedRankingsException;
import com.pucpr.portplace.features.strategy.services.internal.ScenarioEntityService;
import com.pucpr.portplace.features.strategy.specs.AllRankingsWereCategorizedSpecification;
import com.pucpr.portplace.features.strategy.specs.PortfolioIsNotCompletedSpecification;
import com.pucpr.portplace.features.strategy.specs.ScenarioExistsSpecification;
import com.pucpr.portplace.features.strategy.specs.ScenarioIsNotAuthorizedSpecification;
import com.pucpr.portplace.features.strategy.specs.StrategyExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScenarioValidationService {
    
    private StrategyExistsSpecification strategyExistsSpecification;
    private EvaluationGroupExistsSpecification evaluationGroupExistsSpecification;
    private ScenarioExistsSpecification scenarioExistsSpecification;
    private PortfolioExistsSpecification portfolioExistsSpecification;
    private ScenarioIsNotAuthorizedSpecification scenarioIsNotAuthorizedSpecification;
    private PortfolioIsNotCompletedSpecification portfolioIsNotCompletedSpecification;
    private AllRankingsWereCategorizedSpecification allRankingsWereCategorizedSpecification;

    private ScenarioEntityService scenarioService;

    public void validateBeforeCreation(
        long strategyId,
        long evaluationGroupId, 
        Long portfolioId
    ){

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)){
            throw new StrategyNotFoundException(strategyId);
        }

        if(!evaluationGroupExistsSpecification.isSatisfiedBy(evaluationGroupId)){
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }

        if(!portfolioExistsSpecification.isSatisfiedBy(portfolioId)){
            throw new PortfolioNotFoundException(portfolioId);
        }

    }

    public void validateBeforeUpdate(long scenarioId) {

        if(!scenarioExistsSpecification.isSatisfiedBy(scenarioId)){
            throw new ScenarioNotFoundException(scenarioId);
        }

        if(!scenarioIsNotAuthorizedSpecification.isSatisfiedBy(scenarioId)){
            throw new ScenarioAlreadyAuthorizedException(scenarioId);
        }

        Scenario p = scenarioService.getScenarioById(scenarioId);

        if(!portfolioIsNotCompletedSpecification.isSatisfiedBy(p.getPortfolio().getId())){
            throw new PortfolioAlreadyCompletedException(p.getPortfolio().getId());
        }


    }

    public void validateBeforeDisable(long scenarioId) {
        
        if(!scenarioExistsSpecification.isSatisfiedBy(scenarioId)){
            throw new ScenarioNotFoundException(scenarioId);
        }
        
    }

    public void validateBeforeGet(long scenarioId) {

        if(!scenarioExistsSpecification.isSatisfiedBy(scenarioId)){
            throw new ScenarioNotFoundException(scenarioId);
        }
        
    }

    public void validateBeforeGetAll(long strategyId) {
    
        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)){
            throw new StrategyNotFoundException(strategyId);
        }
    
    }

    public void validateBeforeAuthorization(long scenarioId) {
        
        validateBeforeGet(scenarioId);

        if(!scenarioIsNotAuthorizedSpecification.isSatisfiedBy(scenarioId)){
            throw new ScenarioAlreadyAuthorizedException(scenarioId);
        }

        Scenario p = scenarioService.getScenarioById(scenarioId);

        if(!portfolioIsNotCompletedSpecification.isSatisfiedBy(p.getPortfolio().getId())){
            throw new PortfolioAlreadyCompletedException(p.getPortfolio().getId());
        }

        if(!allRankingsWereCategorizedSpecification.isSatisfiedBy(p.getScenarioRankings())){
            throw new UncategorizedRankingsException(scenarioId);
        }

    }

    public void validateBeforeCancellation(long scenarioId) {

        if(!scenarioExistsSpecification.isSatisfiedBy(scenarioId)){
            throw new ScenarioNotFoundException(scenarioId);
        }

    }

}
