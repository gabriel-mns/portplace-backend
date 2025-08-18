package com.pucpr.portplace.features.ahp.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupUpdateDTO;
import com.pucpr.portplace.features.ahp.exceptions.EvaluationGroupNotFoundException;
import com.pucpr.portplace.features.ahp.exceptions.CriteriaGroupNotFoundException;
import com.pucpr.portplace.features.ahp.specs.EvaluationGroupExistsSpecification;
import com.pucpr.portplace.features.ahp.specs.CriteriaGroupExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationGroupValidationService {

    private CriteriaGroupExistsSpecification criteriaGroupExistsSpecification;
    private EvaluationGroupExistsSpecification egExistsSpecification;

    public void validateBeforeCreation(long strategyId, EvaluationGroupCreateDTO dto) {
        
        // TODO: After implementing Strategy, validate if the strategyId exists
        long criteriaGroupId = dto.getCriteriaGroupId();

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

    public void validateBeforeUpdate(long strategyId, Long evaluationGroupId, EvaluationGroupUpdateDTO dto) {

        // TODO: After implementing Strategy, validate if the strategyId exists

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }

        // long criteriaGroupId = dto.getCriteriaGroupId();

        // if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
        //     throw new CriteriaGroupNotFoundException(criteriaGroupId);
        // }

    }

    public void validateBeforeDisable(long strategyId, Long evaluationGroupId) {
        
        // TODO: After implementing Strategy, validate if the strategyId exists

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }

    }

    public void validateBeforeDelete(long strategyId, Long evaluationGroupId) {
        // TODO: After implementing Strategy, validate if the strategyId exists

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }
        
    }

}
