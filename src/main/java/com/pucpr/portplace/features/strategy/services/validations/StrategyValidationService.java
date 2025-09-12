package com.pucpr.portplace.features.strategy.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.exceptions.StrategyNotFoundException;
import com.pucpr.portplace.features.strategy.specs.StrategyExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategyValidationService {

    private StrategyExistsSpecification strategyExistsSpecification;

    public void validateBeforeGet(long strategyId){

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)){
            throw new StrategyNotFoundException(strategyId);
        }

    }

    public void validateBeforeUpdate(long strategyId){

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)){
            throw new StrategyNotFoundException(strategyId);
        }

    }

    public void validateBeforeDisable(long strategyId){

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)){
            throw new StrategyNotFoundException(strategyId);
        }

    }

    public void validateBeforeCancel(long strategyId) {

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)){
            throw new StrategyNotFoundException(strategyId);
        }
        
    }


}
