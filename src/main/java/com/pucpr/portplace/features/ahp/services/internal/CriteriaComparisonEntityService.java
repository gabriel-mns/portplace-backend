package com.pucpr.portplace.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.ahp.repositories.CriteriaComparisonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriteriaComparisonEntityService {
    
    private CriteriaComparisonRepository criteriaComparisonRepository;

    public void disableCriteriaComparison(Criterion compared, Criterion reference) {
        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findActiveComparisonBetweenCriteria(compared, reference);
        criteriaComparison.setDisabled(true);
        criteriaComparisonRepository.save(criteriaComparison);
    }

    public CriteriaComparison getCriteriaComparisonEntityById(long criteriaComparisonId) {

        //TODO: Treat case when criteriaComparison was not found
        CriteriaComparison criteriaComparison = criteriaComparisonRepository.findById(criteriaComparisonId).get();
    
        return criteriaComparison;

    }

    public boolean existsById(long criteriaComparisonId) {

        return criteriaComparisonRepository.existsById(criteriaComparisonId);

    }

    public boolean existsComparisonBetweenCriteria(Criterion criterion1, Criterion criterion2) {
        return criteriaComparisonRepository.existsComparisonBetweenCriteria(criterion1, criterion2);
    }

}
