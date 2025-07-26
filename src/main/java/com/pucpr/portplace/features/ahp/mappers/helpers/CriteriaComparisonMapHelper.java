package com.pucpr.portplace.features.ahp.mappers.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pucpr.portplace.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.features.ahp.services.internal.CriteriaComparisonEntityService;

@Component
public class CriteriaComparisonMapHelper {
    
    @Autowired
    private CriteriaComparisonEntityService criteriaComparisonEntityService;

    public CriteriaComparison fromId(Long id) {

        return criteriaComparisonEntityService.getCriteriaComparisonEntityById(id);

    }

}
