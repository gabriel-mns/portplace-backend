package com.pucpr.portplace.features.ahp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.features.ahp.enums.CriteriaGroupStatusEnum;

public interface CriteriaGroupRepository extends JpaRepository<CriteriaGroup, Long> {

    Page<CriteriaGroup> findByDisabledFalse(Pageable pageable);
    
    
    Page<CriteriaGroup> findByStrategyId(long strategyId, Pageable pageable);
    
    @Query(
        """
        SELECT cg FROM CriteriaGroup cg
        WHERE cg.strategy.id = :strategyId
        AND (:status IS NULL or cg.status IN :status)
        AND (LOWER(cg.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:includeDisabled = true OR cg.disabled = false)
        """
    )
    Page<CriteriaGroup> findByFilters(
        long strategyId,
        List<CriteriaGroupStatusEnum> status,
        String name,
        Boolean includeDisabled,
        Pageable pageable
    );

}
