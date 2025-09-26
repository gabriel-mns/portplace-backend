package com.pucpr.portplace.features.ahp.services.validations;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupUpdateDTO;
import com.pucpr.portplace.features.ahp.exceptions.EvaluationGroupNotFoundException;
import com.pucpr.portplace.features.ahp.exceptions.CriteriaGroupNotFoundException;
import com.pucpr.portplace.features.ahp.specs.EvaluationGroupExistsSpecification;
import com.pucpr.portplace.features.strategy.exceptions.StrategyNotFoundException;
import com.pucpr.portplace.features.strategy.specs.StrategyExistsSpecification;
import com.pucpr.portplace.features.ahp.specs.CriteriaGroupExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationGroupValidationService {

    private CriteriaGroupExistsSpecification criteriaGroupExistsSpecification;
    private EvaluationGroupExistsSpecification egExistsSpecification;
    private StrategyExistsSpecification strategyExistsSpecification;

    public void validateBeforeCreation(long strategyId, EvaluationGroupCreateDTO dto) {
        
        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)) {
            throw new StrategyNotFoundException(strategyId);
        }
        
        long criteriaGroupId = dto.getCriteriaGroupId();

        if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
            throw new CriteriaGroupNotFoundException(criteriaGroupId);
        }

    }

    public void validateBeforeUpdate(long strategyId, Long evaluationGroupId, EvaluationGroupUpdateDTO dto) {

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)) {
            throw new StrategyNotFoundException(strategyId);
        }

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }

        // long criteriaGroupId = dto.getCriteriaGroupId();

        // if(!criteriaGroupExistsSpecification.isSatisfiedBy(criteriaGroupId)) {
        //     throw new CriteriaGroupNotFoundException(criteriaGroupId);
        // }

    }

    public void validateBeforeDisable(long strategyId, Long evaluationGroupId) {
        
        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)) {
            throw new StrategyNotFoundException(strategyId);
        }

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }

    }

    public void validateBeforeDelete(long strategyId, Long evaluationGroupId) {

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)) {
            throw new StrategyNotFoundException(strategyId);
        }

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }
        
    }

    public void validateBeforeGet(long strategyId, Long evaluationGroupId) {

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)) {
            throw new StrategyNotFoundException(strategyId);
        }

        if(!egExistsSpecification.isSatisfiedBy(evaluationGroupId)) {
            throw new EvaluationGroupNotFoundException(evaluationGroupId);
        }
        
    }

    public void validateBeforeGetAll(long strategyId) {

        if(!strategyExistsSpecification.isSatisfiedBy(strategyId)) {
            throw new StrategyNotFoundException(strategyId);
        }
        
    }

}
