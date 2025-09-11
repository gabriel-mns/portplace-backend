package com.pucpr.portplace.features.ahp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.ahp.enums.EvaluationGroupStatusEnum;

public interface EvaluationGroupRepository extends JpaRepository<EvaluationGroup, Long> {

    Page<EvaluationGroup> findByDisabledFalse(Pageable pageable);

    Page<EvaluationGroup> findByStrategyIdAndDisabledFalse(Long strategyId, Pageable pageable);

    Page<EvaluationGroup> findByStrategyId(Long strategyId, Pageable pageable);

    @Query("""
        SELECT eg
        FROM EvaluationGroup eg
        WHERE eg.strategy.id = :strategyId
        AND LOWER(eg.name) LIKE LOWER(CONCAT('%', :name, '%'))
        AND (:includeDisabled = true OR eg.disabled = false)
        AND (:status IS NULL OR eg.status IN :status)
    """)
    Page<EvaluationGroup> findByFilters(
        Long strategyId,
        List<EvaluationGroupStatusEnum> status,
        String name,
        boolean includeDisabled,
        Pageable pageable
    );

}
