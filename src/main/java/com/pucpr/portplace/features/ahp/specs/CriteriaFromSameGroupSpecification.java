package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.BiSpecification;
import com.pucpr.portplace.features.ahp.entities.Criterion;

@Component
public class CriteriaFromSameGroupSpecification implements BiSpecification<Criterion, Criterion> {

    @Override
    public boolean isSatisfiedBy(Criterion compared, Criterion reference) {

        return compared.getCriteriaGroup().equals(reference.getCriteriaGroup());
    }
    
}
