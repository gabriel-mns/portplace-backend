package com.pucpr.portplace.features.strategy.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.strategy.entities.Strategy;
import com.pucpr.portplace.features.strategy.enums.StrategyStatusEnum;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    
    Page<Strategy> findByDisabledFalse(Pageable pageable);

    @Query("""
        SELECT s 
        FROM Strategy s 
        WHERE (LOWER(s.name) LIKE LOWER(CONCAT('%', :strategyName, '%')))
        AND (:status IS NULL OR s.status IN :status) 
        AND (:includeDisabled = true OR s.disabled = false)
    """
    )
    Page<Strategy> findByFilters(
        List<StrategyStatusEnum> status,
        String strategyName,
        boolean includeDisabled,
        Pageable pageable
    );

}
