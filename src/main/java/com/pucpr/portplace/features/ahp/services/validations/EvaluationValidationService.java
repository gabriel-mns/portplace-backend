package com.pucpr.portplace.features.ahp.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.ahp.exceptions.EvaluationGroupNotFoundException;
import com.pucpr.portplace.features.ahp.exceptions.CriterionFromDifferentCriteriaGroupException;
import com.pucpr.portplace.features.ahp.exceptions.CriterionNotFoundException;
import com.pucpr.portplace.features.ahp.exceptions.EvaluationFromDifferentEvaluationGroupException;
import com.pucpr.portplace.features.ahp.exceptions.EvaluationNotFoundException;
import com.pucpr.portplace.features.ahp.services.internal.EvaluationGroupEntityService;
import com.pucpr.portplace.features.ahp.services.internal.CriterionEntityService;
import com.pucpr.portplace.features.ahp.specs.EvaluationGroupExistsSpecification;
import com.pucpr.portplace.features.ahp.specs.CriterionExistsSpecification;
import com.pucpr.portplace.features.ahp.specs.CriterionFromSameCriteriaGroupOfEvaluationGroupSpecification;
import com.pucpr.portplace.features.ahp.specs.EvaluationExistsSpecification;
import com.pucpr.portplace.features.ahp.specs.EvaluationIsFromEvaluationGroupSpecification;
import com.pucpr.portplace.features.project.exceptions.ProjectNotFoundException;
import com.pucpr.portplace.features.project.specs.ProjectExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationValidationService {
    
    private EvaluationGroupEntityService egService;
    private CriterionEntityService criterionService;

    private EvaluationGroupExistsSpecification egExistsSpecification;
    private ProjectExistsSpecification projectExistsSpecification;
    private CriterionExistsSpecification criterionExistsSpecification;
    private EvaluationExistsSpecification evaluationExistsSpecification;
    private EvaluationIsFromEvaluationGroupSpecification evaluationIsFromEvaluationGroupSpecification;
    private CriterionFromSameCriteriaGroupOfEvaluationGroupSpecification criterionFromSameCriteriaGroupOfEvaluationGroupSpecification;

    public void validateBeforeCreation(long egId, EvaluationCreateDTO evaluationCreateDTO) {

        if(!egExistsSpecification.isSatisfiedBy(egId)) {
            throw new EvaluationGroupNotFoundException(egId);
        }
        
        if(!criterionExistsSpecification.isSatisfiedBy(evaluationCreateDTO.getCriterionId())) {
            throw new CriterionNotFoundException(evaluationCreateDTO.getCriterionId());
        }

        EvaluationGroup eg = egService.getById(egId);
        Criterion criterion = criterionService.findById(evaluationCreateDTO.getCriterionId());

        if(!criterionFromSameCriteriaGroupOfEvaluationGroupSpecification.isSatisfiedBy(criterion, eg)) {
            throw new CriterionFromDifferentCriteriaGroupException(criterion.getId(), egId);
        }

        if(!projectExistsSpecification.isSatisfiedBy(evaluationCreateDTO.getProjectId())) {
            throw new ProjectNotFoundException(evaluationCreateDTO.getProjectId());
        }

    }

    public void validateBeforeUpdate(long egId, long evaluationId, EvaluationUpdateDTO evaluation) {

        if(!egExistsSpecification.isSatisfiedBy(egId)) {
            throw new EvaluationGroupNotFoundException(egId);
        }

        if(!evaluationExistsSpecification.isSatisfiedBy(evaluationId)) {
            throw new EvaluationNotFoundException(evaluationId);
        }

        if(!evaluationIsFromEvaluationGroupSpecification.isSatisfiedBy(evaluationId, egId)) {
            throw new EvaluationFromDifferentEvaluationGroupException(evaluationId, egId);
        }

    }

    public void validateBeforeDelete(long egId, long evaluationId) {

        if(!egExistsSpecification.isSatisfiedBy(egId)) {
            throw new EvaluationGroupNotFoundException(egId);
        }

        if(!evaluationExistsSpecification.isSatisfiedBy(evaluationId)) {
            throw new EvaluationNotFoundException(evaluationId);
        }

        if(!evaluationIsFromEvaluationGroupSpecification.isSatisfiedBy(evaluationId, egId)) {
            throw new EvaluationFromDifferentEvaluationGroupException(evaluationId, egId);
        }

    }

    public void validateBeforeDisable(long evaluationGroupId, long evaluationId) {

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }

        if(!evaluationExistsSpecification.isSatisfiedBy(evaluationId)) {
            throw new EvaluationNotFoundException(evaluationId);
        }

        if(!evaluationIsFromEvaluationGroupSpecification.isSatisfiedBy(evaluationId, evaluationGroupId)) {
            throw new EvaluationFromDifferentEvaluationGroupException(evaluationId, evaluationGroupId);
        }

    }

    public void validateBeforeGet(long evaluationGroupId, long evaluationId) {
        
        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }

        if(!evaluationExistsSpecification.isSatisfiedBy(evaluationId)) {
            throw new EvaluationNotFoundException(evaluationId);
        }

        if(!evaluationIsFromEvaluationGroupSpecification.isSatisfiedBy(evaluationId, evaluationGroupId)) {
            throw new EvaluationFromDifferentEvaluationGroupException(evaluationId, evaluationGroupId);
        }

    }

    public void validateBeforeGetAll(long evaluationGroupId) {

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }

    }

}
