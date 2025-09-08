package com.pucpr.portplace.features.ahp.services.validations;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.features.ahp.exceptions.CriteriaGroupNotFoundException;
import com.pucpr.portplace.features.ahp.specs.CriteriaGroupExistsSpecification;
import com.pucpr.portplace.features.strategy.exceptions.StrategyNotFoundException;
import com.pucpr.portplace.features.strategy.specs.StrategyExistsSpecification;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CriteriaGroupValidationService {

    private CriteriaGroupExistsSpecification criteriaGroupExistsSpecification;
    private StrategyExistsSpecification strategyExistsSpecification;

    public void validateBeforeCreation(
        long strategyId
    ) {
        
        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)) {
            throw new StrategyNotFoundException(strategyId);
        }

    }
    
    public void validateBeforeUpdate(long strategyId, long criteriaGroupId) {

        // TODO: After implementing the strategy service, check if the strategy exists

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

    public void validateBeforeDelete(long strategyId, long criteriaGroupId) {

        // TODO: After implementing the strategy service, check if the strategy exists

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

    public void validateBeforeGet(long strategyId, long criteriaGroupId) {
        
        // TODO: After implementing the strategy service, check if the strategy exists

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

    public void validateBeforeGetAll(long strategyId) {
        
        // TODO: After implementing the strategy service, check if the strategy exists

    }

}
