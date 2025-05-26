package com.pucpr.portplace.authentication.features.ahp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.AHP;

public interface AHPRepository extends JpaRepository<AHP, Long> {
    
    List<AHP> findByDisabledFalse();

    List<AHP> findByStrategyIdAndDisabledFalse(Long strategyId);
    
    List<AHP> findByStrategyId(Long strategyId);

}
