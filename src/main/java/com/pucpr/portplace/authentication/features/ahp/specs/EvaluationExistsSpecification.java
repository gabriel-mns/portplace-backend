package com.pucpr.portplace.authentication.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.validation.specs.Specification;
import com.pucpr.portplace.authentication.features.ahp.services.internal.EvaluationEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EvaluationExistsSpecification implements Specification<Long> {

    private final EvaluationEntityService evaluationService;

    @Override
    public boolean isSatisfiedBy(Long evaluationId) {
        return evaluationService.existsById(evaluationId);
    }
    
}
