package com.pucpr.portplace.features.strategy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;

public interface ScenarioRankingRepository extends JpaRepository<ScenarioRanking, Long> {
    
    Page<ScenarioRanking> findByDisabledFalse(Pageable pageable);
    Page<ScenarioRanking> findByDisabledFalseAndScenarioId(Long scenarioId, Pageable pageable);
    Page<ScenarioRanking> findByScenarioId(Long scenarioId, Pageable pageable);

    // Search by Project name
    Page<ScenarioRanking> findByDisabledFalseAndProjectNameContainingIgnoreCase(String projectName, Pageable pageable);
    Page<ScenarioRanking> findByDisabledFalseAndScenarioIdAndProjectNameContainingIgnoreCase(Long scenarioId, String projectName, Pageable pageable);
    Page<ScenarioRanking> findByScenarioIdAndProjectNameContainingIgnoreCase(Long scenarioId, String projectName, Pageable pageable);


}
