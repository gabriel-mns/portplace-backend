package com.pucpr.portplace.features.strategy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.strategy.entities.Scenario;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    
    Page<Scenario> findByDisabledFalse(Pageable pageable);
    Page<Scenario> findByDisabledFalseAndStrategyId(Long strategyId, Pageable pageable);
    Page<Scenario> findByStrategyId(Long strategyId, Pageable pageable);

    // Search by Project name
    Page<Scenario> findByDisabledFalseAndNameContainingIgnoreCase(String scenarioName, Pageable pageable);
    Page<Scenario> findByDisabledFalseAndStrategyIdAndNameContainingIgnoreCase(Long strategyId, String scenarioName, Pageable pageable);
    Page<Scenario> findByStrategyIdAndNameContainingIgnoreCase(Long strategyId, String scenarioName, Pageable pageable);

}
