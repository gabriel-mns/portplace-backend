package com.pucpr.portplace.features.portfolio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.portfolio.entities.Risk;

public interface RiskRepository extends JpaRepository<Risk, Long> {

    @Query(
        """
        SELECT r FROM Risk r
        WHERE r.portfolio.id = :portfolioId
            AND (:includeDisabled = true OR r.disabled = false)
            AND (lower(r.name) LIKE lower(concat('%', :searchQuery, '%')))
        """
    )
    Page<Risk> findByFilters(
        Long portfolioId, 
        String searchQuery, 
        boolean includeDisabled, 
        Pageable pageable
    );
    
}
