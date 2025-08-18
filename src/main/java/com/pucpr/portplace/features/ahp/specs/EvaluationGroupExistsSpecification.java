package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.ahp.services.internal.EvaluationGroupEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EvaluationGroupExistsSpecification implements Specification<Long> {

    private EvaluationGroupEntityService egService;

    @Override
    public boolean isSatisfiedBy(Long evaluationGroupId) {
        return egService.existsById(evaluationGroupId);
    }
    
}
