package com.pucpr.portplace.features.ahp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.ahp.entities.AHP;

public interface AHPRepository extends JpaRepository<AHP, Long> {

    Page<AHP> findByDisabledFalse(Pageable pageable);

    Page<AHP> findByStrategyIdAndDisabledFalse(Long strategyId, Pageable pageable);

    Page<AHP> findByStrategyId(Long strategyId, Pageable pageable);

}
