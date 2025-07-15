package com.pucpr.portplace.authentication.features.ahp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.ahp.dtos.ProjectRankingReadDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.entities.Evaluation;

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

        // 1) Get all unique criteria IDs from comparisons
        List<Long> criteriaIds = comparisons.stream()
            .flatMap(cmp -> Stream.of(
                cmp.getComparedCriterion().getId(),
                cmp.getReferenceCriterion().getId()))
            .distinct()
            .sorted() // opcional: ordem crescente
            .collect(Collectors.toList());

        int criteriaSize = criteriaIds.size();

        // Maps criteria ID to its index in the matrix
        Map<Long,Integer> indexById = new HashMap<>();
        for (int i = 0; i < criteriaSize; i++) {
            indexById.put(criteriaIds.get(i), i);
        }

        // 2) Initialize matrix of values me→others, with 1.0 on the diagonal (self-comparison)
        double[][] matrix = new double[criteriaSize][criteriaSize];
        for (int i = 0; i < criteriaSize; i++) {
            matrix[i][i] = 1.0;
        }

        // 3) For each criteria comparison, fill the matrix with values
        
        for (CriteriaComparison cmp : comparisons) {
            int me      = indexById.get(cmp.getComparedCriterion().getId());   // eu
            int other   = indexById.get(cmp.getReferenceCriterion().getId());  // outro
            double v    = cmp.getImportanceScale().getValue();               // eu→outro
            double r    = cmp.getImportanceScale().getReciprocal();          // outro→eu

            matrix[me][other] = v;
            matrix[other][me] = r;
        }

        // 4) Soma cada coluna
        double[] colSum = new double[criteriaSize];
        for (int j = 0; j < criteriaSize; j++) {
            double sum = 0;
            for (int i = 0; i < criteriaSize; i++) {
                sum += matrix[i][j];
            }
            colSum[j] = sum;
        }

        // 5) Normaliza colunas e calcula a média de cada linha
        Map<Long, Double> criteriaWeights = new HashMap<>();
        for (int i = 0; i < criteriaSize; i++) {
            double sumNormalized = 0;
            
            for (int j = 0; j < criteriaSize; j++) {
                sumNormalized += matrix[i][j] / colSum[j];
            }
            
            double average = sumNormalized / criteriaSize;
            criteriaWeights.put(criteriaIds.get(i), average);
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

    public List<ProjectRankingReadDTO> getProjectRankingByAHPId(Long ahpId) {
        
        AHP ahp = ahpService.getAHPEntityById(ahpId);

        List<Evaluation> evaluations = ahp.getEvaluations();
        List<CriteriaComparison> criteriaComparisons = ahp.getCriteriaGroup().getCriteriaComparisons();

        List<ProjectRankingReadDTO> ranking = calculateProjectRanking(evaluations, criteriaComparisons);

        return ranking;

    }

    public double getCriterionWeight(Long criterionId, List<CriteriaComparison> criteriaComparisons) {

        Map<Long, Double> criterionWeights = calculateCriteriaWeights(criteriaComparisons);

        return criterionWeights.getOrDefault(criterionId, 0.0);
        
    }

}