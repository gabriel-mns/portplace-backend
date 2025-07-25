package com.pucpr.portplace.authentication.features.ahp.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.exceptions.AHPNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.CriterionNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.EvaluationFromDifferentAHPException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.EvaluationNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.services.internal.AHPEntityService;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriterionEntityService;
import com.pucpr.portplace.authentication.features.ahp.specs.AHPExistsSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.CriterionExistsSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.CriterionFromSameCriteriaGroupOfAHPSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.EvaluationExistsSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.EvaluationIsFromAHPSpecification;
import com.pucpr.portplace.authentication.features.project.exceptions.ProjectNotFoundException;
import com.pucpr.portplace.authentication.features.project.specs.ProjectExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationValidationService {
    
    private AHPEntityService ahpService;
    private CriterionEntityService criterionService;

    private AHPExistsSpecification ahpExistsSpecification;
    private ProjectExistsSpecification projectExistsSpecification;
    private CriterionExistsSpecification criterionExistsSpecification;
    private EvaluationExistsSpecification evaluationExistsSpecification;
    private EvaluationIsFromAHPSpecification evaluationIsFromAHPSpecification;
    private CriterionFromSameCriteriaGroupOfAHPSpecification criterionFromSameCriteriaGroupOfAHPSpecification;

    public void validateBeforeCreate(long ahpId, EvaluationCreateDTO evaluationCreateDTO) {

        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }
        
        if(!criterionExistsSpecification.isSatisfiedBy(evaluationCreateDTO.getCriterionId())) {
            throw new CriterionNotFoundException(evaluationCreateDTO.getCriterionId());
        }

        AHP ahp = ahpService.getById(ahpId);
        Criterion criterion = criterionService.findById(evaluationCreateDTO.getCriterionId());

        if(!criterionFromSameCriteriaGroupOfAHPSpecification.isSatisfiedBy(criterion, ahp)) {
            throw new CriterionFromDifferentCriteriaGroupException(criterion.getId(), ahpId);
        }

        if(!projectExistsSpecification.isSatisfiedBy(evaluationCreateDTO.getProjectId())) {
            throw new ProjectNotFoundException(evaluationCreateDTO.getProjectId());
        }

    }

    public void validateBeforeUpdate(long ahpId, long evaluationId, EvaluationUpdateDTO evaluation) {

        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }

        if(!evaluationExistsSpecification.isSatisfiedBy(evaluationId)) {
            throw new EvaluationNotFoundException(evaluationId);
        }

        if(!evaluationIsFromAHPSpecification.isSatisfiedBy(evaluationId, ahpId)) {
            throw new EvaluationFromDifferentAHPException(evaluationId, ahpId);
        }

    }

    public void validateBeforeDelete(long ahpId, long evaluationId) {

        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }

        if(!evaluationExistsSpecification.isSatisfiedBy(evaluationId)) {
            throw new EvaluationNotFoundException(evaluationId);
        }

        if(!evaluationIsFromAHPSpecification.isSatisfiedBy(evaluationId, ahpId)) {
            throw new EvaluationFromDifferentAHPException(evaluationId, ahpId);
        }

    }

    public void validateBeforeDisable(long ahpId, long evaluationId) {

        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }

        if(!evaluationExistsSpecification.isSatisfiedBy(evaluationId)) {
            throw new EvaluationNotFoundException(evaluationId);
        }

        if(!evaluationIsFromAHPSpecification.isSatisfiedBy(evaluationId, ahpId)) {
            throw new EvaluationFromDifferentAHPException(evaluationId, ahpId);
        }

    }

    public void validateBeforeGet(long ahpId, long evaluationId) {
        
        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }

        if(!evaluationExistsSpecification.isSatisfiedBy(evaluationId)) {
            throw new EvaluationNotFoundException(evaluationId);
        }

        if(!evaluationIsFromAHPSpecification.isSatisfiedBy(evaluationId, ahpId)) {
            throw new EvaluationFromDifferentAHPException(evaluationId, ahpId);
        }

    }

    public void validateBeforeGetAll(long ahpId) {

        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }

    }

}
