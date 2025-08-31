package com.pucpr.portplace.features.strategy.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.ahp.dtos.ProjectRankingReadDTO;
import com.pucpr.portplace.features.ahp.services.AHPResultsService;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.project.services.internal.ProjectEntityService;
import com.pucpr.portplace.features.strategy.dtos.ScenarioRankingReadDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioRankingUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;
import com.pucpr.portplace.features.strategy.enums.ScenarioRankingStatusEnum;
import com.pucpr.portplace.features.strategy.mappers.ScenarioRankingMapper;
import com.pucpr.portplace.features.strategy.repositories.ScenarioRankingRepository;
import com.pucpr.portplace.features.strategy.services.validations.ScenarioRankingValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScenarioRankingService {
    
    private AHPResultsService ahpResultsService;
    private ProjectEntityService projectEntityService;
    private ScenarioRankingMapper mapper;
    private ScenarioRankingRepository repository;
    private ScenarioRankingValidationService validationService;

    public List<ScenarioRanking> calculateRankings(
        long evaluationGroupId,
        double budget
    ) {

        List<ProjectRankingReadDTO> ranking = ahpResultsService.getProjectRankingByEvaluationGroupId(evaluationGroupId);
        ranking.sort(Comparator.comparingDouble(ProjectRankingReadDTO::getPosition));

        List<ScenarioRanking> scenarioRankings = new ArrayList<>();

        double accumulatedCost = 0.0;

        for (ProjectRankingReadDTO position : ranking) {
            
            Project project = projectEntityService.getProjectEntityById(position.getProjectId());
            
            ScenarioRanking scenarioRanking = new ScenarioRanking();
            
            // scenarioRanking.setCustomPosition(position.getPosition());
            scenarioRanking.setCalculatedPosition(position.getPosition());
            scenarioRanking.setTotalScore(position.getTotalScore());

            if(accumulatedCost + project.getBudget() > budget) {
                scenarioRanking.setStatus(ScenarioRankingStatusEnum.EXCLUDED);
            } else {
                scenarioRanking.setStatus(ScenarioRankingStatusEnum.INCLUDED);
                accumulatedCost += project.getBudget();
            }

            scenarioRanking.setProject(project);

            scenarioRankings.add(scenarioRanking);

        }

        return scenarioRankings;

    }

    public ScenarioRankingReadDTO updateScenarioRanking(
        ScenarioRankingUpdateDTO dto, 
        long scenarioId,
        long rankingId
    ) {
        
        validationService.validateBeforeUpdate(scenarioId, rankingId);

        // Scenario scenario = scenarioEntityService.getScenarioById(scenarioId);
        ScenarioRanking sr = repository.findById(rankingId).get();

        // reorderLinked(scenario.getScenarioRankings(),sr.getCustomPosition(), dto.getCustomPosition());

        mapper.updateFromDTO(dto, sr);
        
        sr = repository.save(sr);

        return mapper.toReadDTO(sr);

    }

    // private void reorderLinked(List<ScenarioRanking> rankings, int oldPos, int newPos) {
    //     // Encontrar o item
    //     ScenarioRanking item = rankings.stream()
    //             .filter(r -> r.getCustomPosition() == oldPos)
    //             .findFirst()
    //             .orElseThrow(() -> new IllegalArgumentException("Posição inválida"));

    //     // Remove do lugar antigo
    //     rankings.remove(item);

    //     // Insere no novo lugar
    //     rankings.add(newPos - 1, item);

    //     // Reatribui posições
    //     int pos = 1;
    //     for (ScenarioRanking r : rankings) {
    //         r.setCustomPosition(pos++);
    //     }
    // }

    public Page<ScenarioRankingReadDTO> getAllRankingsByScenarioId(
        long scenarioId, 
        String projectName, 
        Pageable pageable
    ) {
        
        validationService.validateBeforeGetAll(scenarioId);

        Page<ScenarioRanking> rankings = repository.findByScenarioIdAndProjectNameContainingIgnoreCase(scenarioId, projectName, pageable);

        return rankings.map(mapper :: toReadDTO);
        
    }
    
}
