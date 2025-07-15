package com.pucpr.portplace.authentication.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriteriaComparisonRepository;

@Service
public class CriteriaComparisonEntityService {
    
    private CriteriaComparisonRepository criteriaComparisonRepository;

    public CriteriaComparison getCriteriaComparisonEntityById(long criteriaComparisonId) {

        //TODO: Treat case when criteriaComparison was not found
        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();
    
        return criteriaComparison;

    }

}
