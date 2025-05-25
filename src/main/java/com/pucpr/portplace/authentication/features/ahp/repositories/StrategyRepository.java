package com.pucpr.portplace.authentication.features.ahp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.Strategy;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    
}
