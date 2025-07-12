package com.pucpr.portplace.authentication.features.ahp.mappers.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.services.CriteriaComparisonService;

@Component
public class CriteriaComparisonMapHelper {
    
    @Autowired
    private CriteriaComparisonService criteriaComparisonService;

    public CriteriaComparison fromId(Long id) {

        return criteriaComparisonService.getCriteriaComparisonEntityById(id);

    }

}
