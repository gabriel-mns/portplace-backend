package com.pucpr.portplace.authentication.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriteriaGroupRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriteriaGroupEntityService {
    
    private CriteriaGroupRepository criteriaGroupRepository;
    
    public CriteriaGroup getById(
        long strategyId, 
        long criteriaGroupId
        ) {

        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        return criteriaGroup;

    }

    public CriteriaGroup getById(
        long criteriaGroupId
        ) {

        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(criteriaGroupId).get();

        return criteriaGroup;

    }

    public boolean existsById(
        long criteriaGroupId
        ) {

        return criteriaGroupRepository.existsById(criteriaGroupId);

    }
    
}
