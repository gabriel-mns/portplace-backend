package com.pucpr.portplace.authentication.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.validation.specs.BiSpecification;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriteriaComparisonEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CriteriaNotComparedSpecification implements BiSpecification<Criterion, Criterion> {

    private CriteriaComparisonEntityService entityService;

    @Override
    public boolean isSatisfiedBy(Criterion criterion1, Criterion criterion2) {

        boolean sameCriteriaGroup = criterion1.getCriteriaGroup().equals(criterion2.getCriteriaGroup());
        boolean comparisonExists = entityService.existsComparisonBetweenCriteria(criterion1, criterion2);

        return sameCriteriaGroup && !comparisonExists;

    }

}
