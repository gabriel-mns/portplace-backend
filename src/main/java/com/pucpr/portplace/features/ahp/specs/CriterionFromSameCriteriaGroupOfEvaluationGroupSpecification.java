package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.BiSpecification;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CriterionFromSameCriteriaGroupOfEvaluationGroupSpecification implements BiSpecification<Criterion, EvaluationGroup> {

    @Override
    public boolean isSatisfiedBy(Criterion criterion, EvaluationGroup evaluationGroup) {
        
        if (criterion == null || evaluationGroup == null) {
            return false;
        }

        long criteriaGroupIdAHP = evaluationGroup.getCriteriaGroup().getId();
        long criteriaGroupIdCriterion = criterion.getCriteriaGroup().getId();

        if (criteriaGroupIdAHP != criteriaGroupIdCriterion){
            return false;
        }

        return true;

    }
    
}
