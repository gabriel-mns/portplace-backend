package com.pucpr.portplace.features.ahp.services.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.ahp.mappers.CriterionMapper;
import com.pucpr.portplace.features.ahp.repositories.CriterionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CriterionEntityService {
    
    private CriterionRepository criterionRepository;
    private CriterionMapper criterionMapper;

    public Criterion findById(long criterionId) {

        return criterionRepository.findById(criterionId).get();

    }

    public Page<CriterionReadDTO> findByStrategicObjectiveId(
        Long strategicObjectiveId,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        Page<Criterion> criteria = criterionRepository.findByStrategicObjectiveId(
            strategicObjectiveId,
            includeDisabled, 
            searchQuery, 
            pageable
        );

        return criteria.map(criterionMapper::toReadDTO);

    }

}
