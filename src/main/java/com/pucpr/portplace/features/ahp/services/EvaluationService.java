package com.pucpr.portplace.features.ahp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationReadDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.AHP;
import com.pucpr.portplace.features.ahp.entities.Evaluation;
import com.pucpr.portplace.features.ahp.mappers.EvaluationMapper;
import com.pucpr.portplace.features.ahp.repositories.EvaluationRepository;
import com.pucpr.portplace.features.ahp.services.internal.AHPEntityService;
import com.pucpr.portplace.features.ahp.services.validations.EvaluationValidationService;

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

        validationService.validateBeforeCreation(ahpId, evaluationCreateDTO);

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

    public Page<EvaluationReadDTO> getAllEvaluationsByAHPId(long ahpId, boolean includeDisabled, PageRequest pageable) {

        validationService.validateBeforeGetAll(ahpId);

        Page<Evaluation> evaluations;

        if(includeDisabled) {
            evaluations = evaluationRepository.findByAhpId(ahpId, pageable);
        } else {
            evaluations = evaluationRepository.findByAhpIdAndDisabledFalse(ahpId, pageable);
        }

        Page<EvaluationReadDTO> evaluationDTOs = evaluations.map(evaluationMapper::toReadDTO);

        return evaluationDTOs;

    }

}
