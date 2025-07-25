package com.pucpr.portplace.authentication.features.ahp.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.exceptions.ComparisonOfCriteriaFromDifferentGroupsException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.CriteriaComparisonNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.CriteriaGroupNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.CriterionNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.SameCriteriaException;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriterionEntityService;
import com.pucpr.portplace.authentication.features.ahp.specs.ComparedCriteriaAreDifferentSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.CriteriaComparisonExistsSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.CriteriaFromSameGroupSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.CriteriaGroupExistsSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.CriterionExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriteriaComparisonValidationService {
    
    private CriterionEntityService criterionEntityService;

    private CriteriaComparisonExistsSpecification criteriaComparisonExistsSpecification;
    private CriteriaGroupExistsSpecification criteriaGroupExistsSpecification;
    private CriterionExistsSpecification criterionExistsSpecification;
    private ComparedCriteriaAreDifferentSpecification comparedCriteriaAreDifferentSpecification;
    private CriteriaFromSameGroupSpecification criteriaFromSameGroupSpecification;

    public void validateBeforeCreation(
        long strategyId, 
        long criteriaGroupId, 
        long comparedCriterionId, 
        long referenceCriterionId
        ) {
        
        // TODO: After implementing the StrategyService, check if the strategyId is valid

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

        if(!criterionExistsSpecification.isSatisfiedBy(comparedCriterionId)) {
            throw new CriterionNotFoundException(comparedCriterionId);
        }

        if(!criterionExistsSpecification.isSatisfiedBy(referenceCriterionId)) {
            throw new CriterionNotFoundException(referenceCriterionId);
        }

        Criterion compared = criterionEntityService.findById(comparedCriterionId);
        Criterion refCriterion = criterionEntityService.findById(referenceCriterionId);

        if(!comparedCriteriaAreDifferentSpecification.isSatisfiedBy(compared, refCriterion)) {
            throw new SameCriteriaException(compared.getId(), refCriterion.getId());
        }

        if(!criteriaFromSameGroupSpecification.isSatisfiedBy(compared, refCriterion)) {
            throw new ComparisonOfCriteriaFromDifferentGroupsException(compared.getId(), refCriterion.getId());
        }

    }

    public void validateBeforeUpdate(
        long strategyId,
        long criteriaComparisonId
    ) {

        // TODO: After implementing the StrategyService, check if the strategyId is valid

        if(!criteriaComparisonExistsSpecification.isSatisfiedBy(criteriaComparisonId)) {
            throw new CriteriaComparisonNotFoundException(criteriaComparisonId);
        }

    }

    public void validateBeforeDisable(long criteriaComparisonId) {

        if(!criteriaComparisonExistsSpecification.isSatisfiedBy(criteriaComparisonId)) {
            throw new CriteriaComparisonNotFoundException(criteriaComparisonId);
        }

    }

    public void validateBeforeDelete(long criteriaComparisonId) {

        validateBeforeDisable(criteriaComparisonId);

    }

    public void validateBeforeGet(long criteriaComparisonId) {
        
        if(!criteriaComparisonExistsSpecification.isSatisfiedBy(criteriaComparisonId)) {
            throw new CriteriaComparisonNotFoundException(criteriaComparisonId);
        }

    }

    public void validateBeforeGetAll(long criteriaGroupId) {

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

}
