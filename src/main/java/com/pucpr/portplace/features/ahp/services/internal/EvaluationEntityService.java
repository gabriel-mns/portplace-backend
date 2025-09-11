package com.pucpr.portplace.features.ahp.services.internal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.EvaluationReadDTO;
import com.pucpr.portplace.features.ahp.entities.Evaluation;
import com.pucpr.portplace.features.ahp.mappers.EvaluationMapper;
import com.pucpr.portplace.features.ahp.repositories.EvaluationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationEntityService {
    
    private final EvaluationRepository evaluationRepository;
    private EvaluationMapper evaluationMapper;

    public boolean existsById(Long evaluationId) {
        return evaluationRepository.existsById(evaluationId);
    }

    public Evaluation findById(Long evaluationId) {

        return evaluationRepository.findById(evaluationId).get();

    }

    public List<EvaluationReadDTO> findEvaluationsByProjectId(Long projectId) {

        List<Evaluation> evaluations = evaluationRepository.findEvaluationsByProjectId(projectId);

        return evaluations.stream()
            .map(evaluationMapper::toReadDTO)
            .toList();
    }
    
}
