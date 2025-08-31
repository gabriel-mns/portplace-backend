package com.pucpr.portplace.features.strategy.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.strategy.entities.StrategicObjective;
import com.pucpr.portplace.features.strategy.enums.StrategicObjectiveStatusEnum;

public interface StrategicObjectiveRepository extends JpaRepository<StrategicObjective, Long> {
    
    Page<StrategicObjective> findByDisabledFalse(Pageable pageable);
    Page<StrategicObjective> findByDisabledFalseAndStrategyId(Long strategyId, Pageable pageable);
    Page<StrategicObjective> findByStrategyId(Long strategyId, Pageable pageable);
    // Unified search method using JPQL
    @Query("""
        SELECT so FROM StrategicObjective so
        WHERE (:strategyId IS NULL OR so.strategy.id = :strategyId)
        AND (:status IS NULL OR so.status IN :status)
        AND (:includeDisabled = true OR so.disabled = false)
        AND (LOWER(so.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """
    )
    Page<StrategicObjective> findByFilters(
        Long strategyId,
        List<StrategicObjectiveStatusEnum> status, 
        boolean includeDisabled,
        String name,
        Pageable pageable
    );
}
