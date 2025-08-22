package com.pucpr.portplace.features.strategy.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.exceptions.StrategicObjectiveNotFoundException;
import com.pucpr.portplace.features.strategy.exceptions.StrategyNotFoundException;
import com.pucpr.portplace.features.strategy.specs.StrategicObjectiveExistsSpecification;
import com.pucpr.portplace.features.strategy.specs.StrategyExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategicObjectiveValidationService {

    private StrategicObjectiveExistsSpecification objectiveExistsSpecification;
    private StrategyExistsSpecification strategyExistsSpecification;

    public void validateBeforeCreation(long strategyId){

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)){
            throw new StrategyNotFoundException(strategyId);
        }
        
    }

    public void validateBeforeGet(long objectiveId){

        if(!objectiveExistsSpecification.isSatisfiedBy(objectiveId)){
            throw new StrategicObjectiveNotFoundException(objectiveId);
        }

    }

    public void validateBeforeGetAll(long strategyId){

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)){
            throw new StrategyNotFoundException(strategyId);
        }

    }

    public void validateBeforeUpdate(long objectiveId){

        if(!objectiveExistsSpecification.isSatisfiedBy(objectiveId)){
            throw new StrategicObjectiveNotFoundException(objectiveId);
        }

    }

    public void validateBeforeDisable(long objectiveId){

        if(!objectiveExistsSpecification.isSatisfiedBy(objectiveId)){
            throw new StrategicObjectiveNotFoundException(objectiveId);
        }

    }


}
