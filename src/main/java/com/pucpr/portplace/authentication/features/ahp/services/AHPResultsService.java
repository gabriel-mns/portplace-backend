package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.ProjectRankingReadDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.entities.Evaluation;
import com.pucpr.portplace.authentication.features.ahp.enums.ImportanceScale;

@Service
public class AHPResultsService {
    
    @Autowired
    private AHPService ahpService;

    public List<ProjectRankingReadDTO> calculateProjectRanking(
            List<Evaluation> evaluations,
            List<CriteriaComparison> criteriaComparisons
    ) {
        Map<Long, Double> criterionWeights = calculateCriteriaWeights(criteriaComparisons);

        Map<Long, Double> projectScores = calculateProjectWeightedScores(evaluations, criterionWeights);
        Map<Long, String> projectNames = extractProjectNames(evaluations);

        return buildProjectRanking(projectScores, projectNames);
    }

    private Map<Long, Double> calculateCriteriaWeights(List<CriteriaComparison> comparisons) {
        
        Map<Long, Double> criteriaSums = new HashMap<>();

        // Soma os valores
        for (CriteriaComparison comparison : comparisons) {
            
            Long comparedId = comparison.getComparedCriterion().getId();
            Long referenceId = comparison.getReferenceCriterion().getId();

            ImportanceScale scale = comparison.getImportanceScale();

            // Para quem está sendo comparado, soma o valor direto
            criteriaSums.merge(comparedId, scale.getValue(), Double::sum);

            // Para quem está sendo referência, soma o valor recíproco
            criteriaSums.merge(referenceId, scale.getReciprocal(), Double::sum);
        }

        // Opcional: normalizar os valores para somarem 1 (virar porcentagem)
        double totalSum = criteriaSums.values().stream().mapToDouble(Double::doubleValue).sum();

        Map<Long, Double> criteriaWeights = new HashMap<>();
        for (Map.Entry<Long, Double> entry : criteriaSums.entrySet()) {
            criteriaWeights.put(entry.getKey(), entry.getValue() / totalSum);
        }

        return criteriaWeights;

    } 

    private Map<Long, Double> calculateProjectWeightedScores(
            List<Evaluation> evaluations,
            Map<Long, Double> criterionWeights
    ) {
        Map<Long, Double> projectScores = new HashMap<>();

        for (Evaluation eval : evaluations) {
            long projectId = eval.getProject().getId();
            long criterionId = eval.getCriterion().getId();

            double weight = criterionWeights.getOrDefault(criterionId, 0.0);
            double contribution = Math.round(eval.getScore() * weight * 100.0) / 100.0;

            projectScores.merge(projectId, contribution, Double::sum);
        }

        return projectScores;
    }

    private Map<Long, String> extractProjectNames(List<Evaluation> evaluations) {
        return evaluations.stream()
                .collect(Collectors.toMap(
                        e -> e.getProject().getId(),
                        e -> e.getProject().getName(),
                        (existing, replacement) -> existing // Keep the first name encountered
                ));
    }

    private List<ProjectRankingReadDTO> buildProjectRanking(
            Map<Long, Double> projectScores,
            Map<Long, String> projectNames
    ) {
        List<Map.Entry<Long, Double>> ordered = projectScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .toList();

        List<ProjectRankingReadDTO> ranking = new ArrayList<>();
        int position = 1;

        for (Map.Entry<Long, Double> entry : ordered) {
            ranking.add(new ProjectRankingReadDTO(
                    entry.getKey(),
                    projectNames.get(entry.getKey()),
                    position,
                    entry.getValue()
            ));
            position++;
        }

        return ranking;
    }

    public ResponseEntity<List<ProjectRankingReadDTO>> getProjectRankingByAHPId(Long ahpId) {
        
        AHP ahp = ahpService.getAHPEntityById(ahpId);

        List<Evaluation> evaluations = ahp.getEvaluations();
        List<CriteriaComparison> criteriaComparisons = ahp.getCriteriaGroup().getCriteriaComparisons();

        List<ProjectRankingReadDTO> ranking = calculateProjectRanking(evaluations, criteriaComparisons);

        return ResponseEntity.ok(ranking);

    }


}