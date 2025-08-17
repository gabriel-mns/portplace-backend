package com.pucpr.portplace.features.ahp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.ahp.entities.AHP;

public interface AHPRepository extends JpaRepository<AHP, Long> {

    Page<AHP> findByDisabledFalse(Pageable pageable);

    Page<AHP> findByStrategyIdAndDisabledFalse(Long strategyId, Pageable pageable);

    Page<AHP> findByStrategyId(Long strategyId, Pageable pageable);

    @Query("""
        SELECT ahp
        FROM AHP ahp
        WHERE LOWER(ahp.name) LIKE LOWER(CONCAT('%', :name, '%'))
        AND (:includeDisabled = true OR ahp.disabled = false)
    """)
    Page<AHP> findByName(String name, boolean includeDisabled, Pageable pageable);

}
