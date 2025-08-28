package com.pucpr.portplace.features.strategy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.strategy.entities.Strategy;
import com.pucpr.portplace.features.strategy.enums.StrategyStatusEnum;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    
    Page<Strategy> findByDisabledFalse(Pageable pageable);

    Page<Strategy> findByStatus(StrategyStatusEnum status, Pageable pageable);

    Page<Strategy> findByStatusAndDisabledFalse(StrategyStatusEnum status, Pageable pageable);

}
