package com.pucpr.portplace.authentication.features.ahp.services.validations;

import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.authentication.features.ahp.exceptions.AHPNotFoundException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.NotAllCriteriaComparedException;
import com.pucpr.portplace.authentication.features.ahp.exceptions.NotAllProjectsEvaluatedException;
import com.pucpr.portplace.authentication.features.ahp.services.internal.AHPEntityService;
import com.pucpr.portplace.authentication.features.ahp.specs.AHPExistsSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.AllCriteriaComparedSpecification;
import com.pucpr.portplace.authentication.features.ahp.specs.AllProjectsEvaluatedSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPResultsValidationService {

    private AHPEntityService ahpEntityService;

    private AHPExistsSpecification ahpExistsSpecification;
    private AllCriteriaComparedSpecification allCriteriaComparedSpecification;
    private AllProjectsEvaluatedSpecification allProjectsEvaluatedSpecification;

    public void validateBeforeGet(Long ahpId) {

        if(!ahpExistsSpecification.isSatisfiedBy(ahpId)) {
            throw new AHPNotFoundException(ahpId);
        }

        AHP ahp = ahpEntityService.getById(ahpId);

        CriteriaGroup criteriaGroup = ahp.getCriteriaGroup();

        if(!allCriteriaComparedSpecification.isSatisfiedBy(criteriaGroup)) {
            throw new NotAllCriteriaComparedException();
        }

        if(!allProjectsEvaluatedSpecification.isSatisfiedBy(ahp)) {
            throw new NotAllProjectsEvaluatedException();
        }

    }

}
