package com.pucpr.portplace.features.strategy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.strategy.entities.Strategy;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    
    Page<Strategy> findByDisabledFalse(Pageable pageable);

}
