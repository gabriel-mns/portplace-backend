package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.BiSpecification;
import com.pucpr.portplace.features.ahp.services.internal.EvaluationEntityService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EvaluationIsFromEvaluationGroupSpecification implements BiSpecification<Long, Long> {

    private EvaluationEntityService evaluationService;

    @Override
    public boolean isSatisfiedBy(Long evaluationId, Long evaluationGroupId) {

        Long evaluationEGId = getEvaluationGroupIdFromEvaluation(evaluationId);

        return evaluationEGId != null && evaluationEGId.equals(evaluationGroupId);
        
    }

    @Transactional
    private Long getEvaluationGroupIdFromEvaluation(Long evaluationId) {

        return evaluationService.findById(evaluationId).getEvaluationGroup().getId();

    }
    
}
