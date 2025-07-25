package com.pucpr.portplace.authentication.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.validation.specs.BiSpecification;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CriterionFromSameCriteriaGroupOfAHPSpecification implements BiSpecification<Criterion, AHP> {

    @Override
    public boolean isSatisfiedBy(Criterion criterion, AHP ahp) {
        
        if (criterion == null || ahp == null) {
            return false;
        }

        long criteriaGroupIdAHP = ahp.getCriteriaGroup().getId();
        long criteriaGroupIdCriterion = criterion.getCriteriaGroup().getId();

        if (criteriaGroupIdAHP != criteriaGroupIdCriterion){
            return false;
        }

        return true;

    }
    
}
