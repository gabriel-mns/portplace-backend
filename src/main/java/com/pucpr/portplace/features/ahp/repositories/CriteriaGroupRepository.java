package com.pucpr.portplace.features.ahp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;

public interface CriteriaGroupRepository extends JpaRepository<CriteriaGroup, Long> {

    Page<CriteriaGroup> findByDisabledFalse(Pageable pageable);
    
    Page<CriteriaGroup> findByStrategyIdAndDisabledFalse(long strategyId, Pageable pageable);
    
    Page<CriteriaGroup> findByStrategyId(long strategyId, Pageable pageable);

}
