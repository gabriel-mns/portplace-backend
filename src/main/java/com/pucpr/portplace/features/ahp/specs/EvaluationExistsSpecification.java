package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.ahp.services.internal.EvaluationEntityService;

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
