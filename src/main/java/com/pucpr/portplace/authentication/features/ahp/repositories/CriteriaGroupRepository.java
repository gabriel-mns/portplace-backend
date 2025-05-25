package com.pucpr.portplace.authentication.features.ahp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;

public interface CriteriaGroupRepository extends JpaRepository<CriteriaGroup, Long> {

    List<CriteriaGroup> findByDisabledFalse();
    
    List<CriteriaGroup> findByStrategyIdAndDisabledFalse(long strategyId);
    
    List<CriteriaGroup> findByStrategyId(long strategyId);

}
