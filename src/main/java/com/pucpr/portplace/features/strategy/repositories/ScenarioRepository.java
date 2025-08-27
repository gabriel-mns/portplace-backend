package com.pucpr.portplace.features.strategy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pucpr.portplace.features.strategy.entities.Scenario;
import com.pucpr.portplace.features.strategy.enums.ScenarioStatusEnum;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    
    Page<Scenario> findByDisabledFalse(Pageable pageable);
    Page<Scenario> findByDisabledFalseAndStrategyId(Long strategyId, Pageable pageable);
    Page<Scenario> findByStrategyId(Long strategyId, Pageable pageable);

    // Search by Project name
    Page<Scenario> findByDisabledFalseAndNameContainingIgnoreCase(String scenarioName, Pageable pageable);
    Page<Scenario> findByDisabledFalseAndStrategyIdAndNameContainingIgnoreCase(Long strategyId, String scenarioName, Pageable pageable);
    Page<Scenario> findByStrategyIdAndNameContainingIgnoreCase(Long strategyId, String scenarioName, Pageable pageable);

    @Query("""
        SELECT s FROM Scenario s
        WHERE s.strategy.id = :strategyId
          AND LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))
          AND (:status IS NULL OR s.status = :status)
          AND (:includeDisabled = TRUE OR s.disabled = FALSE)
    """)
    Page<Scenario> findByFilters(
        @Param("strategyId") Long strategyId,
        @Param("name") String name,
        @Param("status") ScenarioStatusEnum status,
        @Param("includeDisabled") boolean includeDisabled,
        Pageable pageable
    );

}
