package com.pucpr.portplace.features.ahp.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.features.ahp.exceptions.EvaluationGroupNotFoundException;
import com.pucpr.portplace.features.ahp.exceptions.NotAllCriteriaComparedException;
import com.pucpr.portplace.features.ahp.exceptions.NotAllProjectsEvaluatedException;
import com.pucpr.portplace.features.ahp.services.internal.EvaluationGroupEntityService;
import com.pucpr.portplace.features.ahp.specs.EvaluationGroupExistsSpecification;
import com.pucpr.portplace.features.ahp.specs.AllCriteriaComparedSpecification;
import com.pucpr.portplace.features.ahp.specs.AllProjectsEvaluatedSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPResultsValidationService {

    private EvaluationGroupEntityService egEntityService;

    private EvaluationGroupExistsSpecification egExistsSpecification;
    private AllCriteriaComparedSpecification allCriteriaComparedSpecification;
    private AllProjectsEvaluatedSpecification allProjectsEvaluatedSpecification;

    public void validateBeforeGet(Long evaluationGroupId) {

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }

        EvaluationGroup eg = egEntityService.getById(evaluationGroupId);

        CriteriaGroup criteriaGroup = eg.getCriteriaGroup();

        if(!allCriteriaComparedSpecification.isSatisfiedBy(criteriaGroup)) {
            throw new NotAllCriteriaComparedException();
        }

        if(!allProjectsEvaluatedSpecification.isSatisfiedBy(eg)) {
            throw new NotAllProjectsEvaluatedException();
        }

    }

}
