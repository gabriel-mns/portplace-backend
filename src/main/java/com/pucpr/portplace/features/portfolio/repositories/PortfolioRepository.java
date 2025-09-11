package com.pucpr.portplace.features.portfolio.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>{

    @Query("""
        SELECT p FROM Portfolio p
        WHERE (:status IS NULL OR p.status IN :status)
        AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        AND (:includeDisabled = true OR p.disabled = false)
    """)
    Page<Portfolio> findByFilters(
        List<PortfolioStatusEnum> status, 
        String searchQuery, 
        boolean includeDisabled, 
        Pageable pageable
    );


    @Query("""
        SELECT DISTINCT p FROM Portfolio p
        JOIN p.scenarios s
        JOIN s.evaluationGroup eg
        JOIN eg.criteriaGroup cg
        JOIN cg.criteria c
        JOIN c.strategicObjectives o
        WHERE o.id = :objectiveId
          AND p.disabled = false
          AND s.disabled = false
          AND eg.disabled = false
          AND cg.disabled = false
          AND c.disabled = false
          AND o.disabled = false
          AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
    """)
    Page<Portfolio> findByObjectiveId(
        Long objectiveId,
        String searchQuery,
        Pageable pageable
    );
    
}
