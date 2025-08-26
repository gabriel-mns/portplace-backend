package com.pucpr.portplace.features.ahp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationReadDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.ahp.entities.Evaluation;
import com.pucpr.portplace.features.ahp.mappers.EvaluationMapper;
import com.pucpr.portplace.features.ahp.repositories.EvaluationRepository;
import com.pucpr.portplace.features.ahp.services.internal.EvaluationGroupEntityService;
import com.pucpr.portplace.features.ahp.services.validations.EvaluationValidationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationService {

    private EvaluationRepository evaluationRepository;
    private EvaluationMapper evaluationMapper; 
    private EvaluationValidationService validationService;
    private EvaluationGroupEntityService egEntityService;

    //CREATE

    @Transactional
    public EvaluationReadDTO createEvaluation(long evaluationGroupId, EvaluationCreateDTO evaluationCreateDTO) {

        validationService.validateBeforeCreation(evaluationGroupId, evaluationCreateDTO);

        Evaluation evaluation = evaluationMapper.toEntity(evaluationCreateDTO);

        EvaluationGroup eg = egEntityService.getById(evaluationGroupId);

        evaluation.setEvaluationGroup(eg);

        evaluation = evaluationRepository.save(evaluation);

        return evaluationMapper.toReadDTO(evaluation);
    }

    // UPDATE
    public EvaluationReadDTO updateEvaluation(long evaluationGroupId, long evaluationId, EvaluationUpdateDTO evaluation) {
        
        validationService.validateBeforeUpdate(evaluationGroupId, evaluationId, evaluation);
        
        Evaluation existingEvaluation = evaluationRepository.findById(evaluationId).get();

        evaluationMapper.updateFromDTO(evaluation, existingEvaluation);

        evaluationRepository.save(existingEvaluation);

        return evaluationMapper.toReadDTO(existingEvaluation);
        
    }

    // DELETE
    public void disableEvaluation(long evaluationGroupId, long evaluationId) {

        validationService.validateBeforeDisable(evaluationGroupId, evaluationId);
        
        Evaluation evaluation = evaluationRepository.findById(evaluationId).get();

        evaluation.setDisabled(true);

        evaluationRepository.save(evaluation);

    }

    public void deleteEvaluation(long evaluationGroupId, long evaluationId) {

        validationService.validateBeforeDelete(evaluationGroupId, evaluationId);

        evaluationRepository.deleteById(evaluationId);

    }

    // READ
    public EvaluationReadDTO getEvaluationById(long evaluationGroupId, long evaluationId) {

        validationService.validateBeforeGet(evaluationGroupId, evaluationId);

        Evaluation evaluation = evaluationRepository.findById(evaluationId).get();

        EvaluationReadDTO evaluationDTO = evaluationMapper.toReadDTO(evaluation);

        return evaluationDTO;
    }

    public Page<EvaluationReadDTO> getAllEvaluationsByEvaluationGroupId(long evaluationGroupId, String projectName, boolean includeDisabled, PageRequest pageable) {

        validationService.validateBeforeGetAll(evaluationGroupId);

        Page<Evaluation> evaluations;
        boolean containsName = projectName != null && !projectName.isEmpty();

        if(containsName) {

            evaluations = evaluationRepository.findByEvaluationGroupIdAndProjectName(evaluationGroupId, projectName, includeDisabled, pageable);

        } else {

            if(includeDisabled) {
                evaluations = evaluationRepository.findByEvaluationGroupId(evaluationGroupId, pageable);
            } else {
                evaluations = evaluationRepository.findByEvaluationGroupIdAndDisabledFalse(evaluationGroupId, pageable);
            }

        }

        Page<EvaluationReadDTO> evaluationDTOs = evaluations.map(evaluationMapper::toReadDTO);

        return evaluationDTOs;

    }

}
