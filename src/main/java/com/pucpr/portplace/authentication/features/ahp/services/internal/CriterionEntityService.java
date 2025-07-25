package com.pucpr.portplace.authentication.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriterionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriterionEntityService {
    
    private CriterionRepository criterionRepository;

    public Criterion findById(long criterionId) {

        return criterionRepository.findById(criterionId).get();

    }

}
