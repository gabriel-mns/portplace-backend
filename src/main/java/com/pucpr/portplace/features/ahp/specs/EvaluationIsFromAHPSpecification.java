package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.BiSpecification;
import com.pucpr.portplace.features.ahp.services.internal.EvaluationEntityService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EvaluationIsFromAHPSpecification implements BiSpecification<Long, Long> {

    private EvaluationEntityService evaluationService;

    @Override
    public boolean isSatisfiedBy(Long evaluationId, Long ahpId) {

        Long evaluationAHPId = getAHPIdFromEvaluation(evaluationId);

        return evaluationAHPId != null && evaluationAHPId.equals(ahpId);
        
    }

    @Transactional
    private Long getAHPIdFromEvaluation(Long evaluationId) {

        return evaluationService.findById(evaluationId).getAhp().getId();

    }
    
}
