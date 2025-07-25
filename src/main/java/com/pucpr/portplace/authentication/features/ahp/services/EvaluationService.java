package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.Evaluation;
import com.pucpr.portplace.authentication.features.ahp.mappers.EvaluationMapper;
import com.pucpr.portplace.authentication.features.ahp.repositories.EvaluationRepository;
import com.pucpr.portplace.authentication.features.ahp.services.internal.AHPEntityService;
import com.pucpr.portplace.authentication.features.ahp.services.validations.EvaluationValidationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationService {

    private EvaluationRepository evaluationRepository;
    private EvaluationMapper evaluationMapper; 
    private EvaluationValidationService validationService;
    private AHPEntityService ahpEntityService;

    //CREATE

    @Transactional
    public EvaluationReadDTO createEvaluation(long ahpId, EvaluationCreateDTO evaluationCreateDTO) {

        validationService.validateBeforeCreate(ahpId, evaluationCreateDTO);

        Evaluation evaluation = evaluationMapper.toEntity(evaluationCreateDTO);

        AHP ahp = ahpEntityService.getById(ahpId);

        evaluation.setAhp(ahp);

        evaluation = evaluationRepository.save(evaluation);

        return evaluationMapper.toReadDTO(evaluation);
    }

    // UPDATE
    public EvaluationReadDTO updateEvaluation(long ahpId, long evaluationId, EvaluationUpdateDTO evaluation) {
        
        validationService.validateBeforeUpdate(ahpId, evaluationId, evaluation);
        
        Evaluation existingEvaluation = evaluationRepository.findById(evaluationId).get();

        evaluationMapper.updateFromDTO(evaluation, existingEvaluation);

        evaluationRepository.save(existingEvaluation);

        return evaluationMapper.toReadDTO(existingEvaluation);
        
    }

    // DELETE
    public void disableEvaluation(long ahpId, long evaluationId) {

        validationService.validateBeforeDisable(ahpId, evaluationId);
        
        Evaluation evaluation = evaluationRepository.findById(evaluationId).get();

        evaluation.setDisabled(true);

        evaluationRepository.save(evaluation);

    }

    public void deleteEvaluation(long ahpId, long evaluationId) {

        validationService.validateBeforeDelete(ahpId, evaluationId);

        evaluationRepository.deleteById(evaluationId);

    }

    // READ
    public EvaluationReadDTO getEvaluationById(long ahpId, long evaluationId) {

        validationService.validateBeforeGet(ahpId, evaluationId);

        Evaluation evaluation = evaluationRepository.findById(evaluationId).get();

        EvaluationReadDTO evaluationDTO = evaluationMapper.toReadDTO(evaluation);

        return evaluationDTO;
    }

    public List<EvaluationReadDTO> getAllEvaluationsByAHPId(long ahpId, boolean includeDisabled) {

        validationService.validateBeforeGetAll(ahpId);

        List<Evaluation> evaluations;

        if(includeDisabled) {

            evaluations = evaluationRepository.findByAhpId(ahpId);

        } else {

            evaluations = evaluationRepository.findByAhpIdAndDisabledFalse(ahpId);

        }

        List<EvaluationReadDTO> evaluationDTOs = evaluationMapper.toReadDTO(evaluations);

        return evaluationDTOs;

    }

}
