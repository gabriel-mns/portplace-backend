package com.pucpr.portplace.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.ahp.repositories.EvaluationGroupRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationGroupEntityService {
    
    private final EvaluationGroupRepository egRepository;

    public EvaluationGroup getById(Long id) {

        return egRepository.findById(id).get();

    }

    public boolean existsById(Long evaluationGroupId) {
        
        return egRepository.existsById(evaluationGroupId);
        
    }

}
