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
    
}
