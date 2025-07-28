package com.pucpr.portplace.authentication.features.ahp.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.entities.Evaluation;
import com.pucpr.portplace.authentication.features.ahp.repositories.EvaluationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationEntityService {
    
    private final EvaluationRepository evaluationRepository;

    public boolean existsById(Long evaluationId) {
        return evaluationRepository.existsById(evaluationId);
    }

    public Evaluation findById(Long evaluationId) {

        return evaluationRepository.findById(evaluationId).get();

    }
    
}
