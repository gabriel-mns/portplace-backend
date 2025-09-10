package com.pucpr.portplace.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.features.ahp.repositories.CriteriaGroupRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriteriaGroupEntityService {
    
    private CriteriaGroupRepository criteriaGroupRepository;
    
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
