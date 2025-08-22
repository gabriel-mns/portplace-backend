package com.pucpr.portplace.features.strategy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.strategy.entities.StrategicObjective;

public interface StrategicObjectiveRepository extends JpaRepository<StrategicObjective, Long> {
    
    Page<StrategicObjective> findByDisabledFalse(Pageable pageable);
    Page<StrategicObjective> findByDisabledFalseAndStrategyId(Long strategyId, Pageable pageable);
    Page<StrategicObjective> findByStrategyId(Long strategyId, Pageable pageable);

    //Search by name
    Page<StrategicObjective> findByDisabledFalseAndNameContainingIgnoreCase(String name, Pageable pageable);
    Page<StrategicObjective> findByDisabledFalseAndStrategyIdAndNameContainingIgnoreCase(Long strategyId, String name, Pageable pageable);
    Page<StrategicObjective> findByStrategyIdAndNameContainingIgnoreCase(Long strategyId, String name, Pageable pageable);
    

}
