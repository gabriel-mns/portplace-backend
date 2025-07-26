package com.pucpr.portplace.features.ahp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.ProjectRankingReadDTO;
import com.pucpr.portplace.features.ahp.entities.AHP;
import com.pucpr.portplace.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.features.ahp.entities.Evaluation;
import com.pucpr.portplace.features.ahp.services.internal.AHPCalculationService;
import com.pucpr.portplace.features.ahp.services.internal.AHPEntityService;
import com.pucpr.portplace.features.ahp.services.validations.AHPResultsValidationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AHPResultsService {
    
    private AHPEntityService ahpEntityService;
    private AHPCalculationService calculatorService;
    private AHPResultsValidationService validationService;

    @Transactional
    public List<ProjectRankingReadDTO> getProjectRankingByAHPId(Long ahpId) {
        
        validationService.validateBeforeGet(ahpId);   

        AHP ahp = ahpEntityService.getById(ahpId);

        List<Evaluation> evaluations = ahp.getEvaluations();
        List<CriteriaComparison> criteriaComparisons = ahp.getCriteriaGroup().getCriteriaComparisons();

        List<ProjectRankingReadDTO> ranking = calculatorService.calculateProjectRanking(evaluations, criteriaComparisons);

        return ranking;

    }


}