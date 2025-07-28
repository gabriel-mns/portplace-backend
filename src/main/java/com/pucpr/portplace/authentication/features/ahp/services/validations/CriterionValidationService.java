package com.pucpr.portplace.authentication.features.ahp.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.core.validation.specs.EntityIsEnabledSpecification;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.authentication.features.ahp.exceptions.CriteriaGroupNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.CriterionNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriteriaGroupEntityService;
import com.pucpr.portplace.authentication.features.ahp.specs.CriteriaGroupExistsSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.CriterionExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriterionValidationService {
    
    CriteriaGroupEntityService service;

    // Specifications
    private EntityIsEnabledSpecification entityIsEnabledSpecification;
    private CriteriaGroupExistsSpecification criteriaGroupExistsSpecification;
    private CriterionExistsSpecification criterionExistsSpecification;

    public void validateBeforeCreation(Long criteriaGroupId) {

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

        CriteriaGroup criteriaGroup = service.getById(criteriaGroupId);

        if(!entityIsEnabledSpecification.isSatisfiedBy(criteriaGroup)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

    public void validateBeforeUpdate(Long id) {

        validateBeforeCreation(id);

        if(!criterionExistsSpecification.isSatisfiedBy(id)) {
            throw new CriterionNotFoundException(id);
        }

    }

    public void validateBeforeDeletion(Long id) {

        if(!criterionExistsSpecification.isSatisfiedBy(id)) {
            throw new CriterionNotFoundException(id);
        }

    }

    public void validateBeforeGet(Long criteriaGroupId, Long criterionId) {

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

        if(!criterionExistsSpecification.isSatisfiedBy(criterionId)) {
            throw new CriterionNotFoundException(criterionId);
        }

    }

    public void validateBeforeGetAll(Long criteriaGroupId) {

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

        CriteriaGroup criteriaGroup = service.getById(criteriaGroupId);

        if(!entityIsEnabledSpecification.isSatisfiedBy(criteriaGroup)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

}
