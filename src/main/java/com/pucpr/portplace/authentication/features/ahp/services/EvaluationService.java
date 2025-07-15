package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.Evaluation;
import com.pucpr.portplace.authentication.features.ahp.mappers.EvaluationMapper;
import com.pucpr.portplace.authentication.features.ahp.repositories.EvaluationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationService {

    private EvaluationRepository evaluationRepository;
    private EvaluationMapper evaluationMapper;
    private static final Logger logger = LoggerFactory.getLogger(EvaluationService.class); 

    //CREATE
    public EvaluationReadDTO createEvaluation(long ahpId, EvaluationCreateDTO evaluationCreateDTO) {
        
        Evaluation evaluation = evaluationMapper.toEntity(evaluationCreateDTO);

        evaluationRepository.save(evaluation);

        return evaluationMapper.toReadDTO(evaluation);
    }

    // UPDATE
    public ResponseEntity<Void> updateEvaluation(long ahpId, long evaluationId,EvaluationUpdateDTO evaluation) {
        
        // TODO: Treat the case when the evaluation is not found (try catch)
        Evaluation existingEvaluation = evaluationRepository.findById(evaluationId).get();

        existingEvaluation.setScore(evaluation.getScore());

        evaluationRepository.save(existingEvaluation);

        return ResponseEntity.status(HttpStatus.OK).build();
        
    }

    // DELETE
    public ResponseEntity<Void> disableEvaluation(long ahpId, long evaluationId) {

        //Treat the case when the evaluation is not found (try catch)
        Evaluation evaluation = evaluationRepository.findById(evaluationId).get();

        evaluation.setDisabled(true);

        evaluationRepository.save(evaluation);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    public ResponseEntity<Void> deleteEvaluation(long ahpId, long evaluationId) {

        //Treat the case when the evaluation is not found (try catch)
        evaluationRepository.deleteById(evaluationId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    // READ
    public ResponseEntity<EvaluationReadDTO> getEvaluationById(long ahpId, long evaluationId) {

        Evaluation evaluation = evaluationRepository.findById(evaluationId).get();

        EvaluationReadDTO evaluationDTO = new EvaluationReadDTO();

        evaluationDTO.setId(evaluation.getId());
        evaluationDTO.setScore(evaluation.getScore());
        evaluationDTO.setProjectId(evaluation.getProject().getId());
        evaluationDTO.setCriterionId(evaluation.getCriterion().getId());
        evaluationDTO.setAhpId(evaluation.getAhp().getId());
        evaluationDTO.setLastModifiedAt(evaluation.getLastModifiedAt());
        evaluationDTO.setCreatedAt(evaluation.getCreatedAt());
        evaluationDTO.setDisabled(evaluation.isDisabled());
        // evaluationDTO.setLastModifiedBy(evaluation.getLastModifiedBy().getId());

        return ResponseEntity.ok(evaluationDTO);

    }

    public Evaluation getEvaluationEntityById(long evaluationId) {

        // Treat the case when the evaluation is not found (try catch)
        return evaluationRepository.findById(evaluationId).get();

    }

    public ResponseEntity<List<EvaluationReadDTO>> getAllEvaluationsByAHPId(long ahpId, boolean includeDisabled) {

        List<Evaluation> evaluations;

        if(includeDisabled) {

            evaluations = evaluationRepository.findByAhpId(ahpId);

        } else {

            evaluations = evaluationRepository.findByAhpIdAndDisabledFalse(ahpId);

        }

        List<EvaluationReadDTO> evaluationDTOs = evaluations.stream().map(evaluation -> {

            EvaluationReadDTO evaluationDTO = new EvaluationReadDTO();

            evaluationDTO.setId(evaluation.getId());
            evaluationDTO.setScore(evaluation.getScore());
            evaluationDTO.setProjectId(evaluation.getProject().getId());
            evaluationDTO.setCriterionId(evaluation.getCriterion().getId());
            evaluationDTO.setAhpId(evaluation.getAhp().getId());
            evaluationDTO.setLastModifiedAt(evaluation.getLastModifiedAt());
            evaluationDTO.setCreatedAt(evaluation.getCreatedAt());
            evaluationDTO.setDisabled(evaluation.isDisabled());
            // evaluationDTO.setLastModifiedBy(evaluation.getLastModifiedBy().getId());

            return evaluationDTO;

        }).collect(Collectors.toList());

        return ResponseEntity.ok(evaluationDTOs);

    }

}
