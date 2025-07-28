package com.pucpr.portplace.authentication.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.validation.specs.Specification;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriterionRepository;

@Component
public class CriterionExistsSpecification implements Specification<Long> {

    private final CriterionRepository criterionRepository;

    public CriterionExistsSpecification(CriterionRepository criterionRepository) {
        this.criterionRepository = criterionRepository;
    }

    @Override
    public boolean isSatisfiedBy(Long criterionId) {
        return criterionRepository.existsById(criterionId);
    }
    
}
