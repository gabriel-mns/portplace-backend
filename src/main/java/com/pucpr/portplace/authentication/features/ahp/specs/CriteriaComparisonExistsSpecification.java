package com.pucpr.portplace.authentication.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.validation.specs.Specification;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriteriaComparisonEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CriteriaComparisonExistsSpecification implements Specification<Long> {

    private CriteriaComparisonEntityService criteriaComparisonEntityService;

    @Override
    public boolean isSatisfiedBy(Long criteriaComparisonId) {

        return criteriaComparisonEntityService.existsById(criteriaComparisonId);

    }
    
}
