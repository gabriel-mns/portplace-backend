package com.pucpr.portplace.authentication.features.ahp.services.validations;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.features.ahp.exceptions.CriteriaGroupNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.specs.CriteriaGroupExistsSpecification;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CriteriaGroupValidationService {

    private CriteriaGroupExistsSpecification criteriaGroupExistsSpecification;

    public void validateBeforeCreation(long strategyId) {
        // TODO: After implementing the strategy service, check if the strategy exists
        return;
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
