package com.pucpr.portplace.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.ahp.repositories.CriterionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriterionEntityService {
    
    private CriterionRepository criterionRepository;

    public Criterion findById(long criterionId) {

        return criterionRepository.findById(criterionId).get();

    }

}
