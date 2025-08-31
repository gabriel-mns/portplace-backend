package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;

@Component
public class EvaluationGroupHasEvaluationsSpecification implements Specification<EvaluationGroup> {

    @Override
    public boolean isSatisfiedBy(EvaluationGroup candidate) {
        
        return candidate.getEvaluations().size() > 0;

    }
    
}
