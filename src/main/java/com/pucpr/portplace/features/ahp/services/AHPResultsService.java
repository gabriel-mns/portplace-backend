package com.pucpr.portplace.features.ahp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.ProjectRankingReadDTO;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.features.ahp.entities.Evaluation;
import com.pucpr.portplace.features.ahp.services.internal.AHPCalculationService;
import com.pucpr.portplace.features.ahp.services.internal.EvaluationGroupEntityService;
import com.pucpr.portplace.features.ahp.services.validations.AHPResultsValidationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPResultsService {
    
    private EvaluationGroupEntityService egEntityService;
    private AHPCalculationService calculatorService;
    private AHPResultsValidationService validationService;

    @Transactional
    public List<ProjectRankingReadDTO> getProjectRankingByEvaluationGroupId(Long evaluationGroupId) {
        
        validationService.validateBeforeGet(evaluationGroupId);   

        EvaluationGroup eg = egEntityService.getById(evaluationGroupId);

        List<Evaluation> evaluations = eg.getEvaluations();
        List<CriteriaComparison> criteriaComparisons = eg.getCriteriaGroup().getCriteriaComparisons();

        List<ProjectRankingReadDTO> ranking = calculatorService.calculateProjectRanking(evaluations, criteriaComparisons);

        return ranking;

    }


}