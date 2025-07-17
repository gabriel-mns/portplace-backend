package com.pucpr.portplace.authentication.features.ahp.constraintValidators.criteriaGroupExists;

import com.pucpr.portplace.authentication.features.ahp.services.internal.CriteriaGroupEntityService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CriteriaGroupExistsValidator implements ConstraintValidator<CriteriaGroupExists, Long>{

    CriteriaGroupEntityService service;

    @Override
    public boolean isValid(Long criteriaGroupId, ConstraintValidatorContext context) {
        return service.existsById(criteriaGroupId);
    }

}
