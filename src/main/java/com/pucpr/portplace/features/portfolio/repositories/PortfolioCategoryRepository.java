package com.pucpr.portplace.features.portfolio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.portfolio.entities.PortfolioCategory;

public interface PortfolioCategoryRepository extends JpaRepository<PortfolioCategory, Long> {

    @Query("""
        SELECT pc FROM PortfolioCategory pc
        WHERE (:portfolioId IS NULL OR pc.portfolio.id = :portfolioId)
        AND (LOWER(pc.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        AND (:includeDisabled = TRUE OR pc.disabled = FALSE)
        """)
    Page<PortfolioCategory> findByFilters(
        Long portfolioId, 
        String searchQuery, 
        boolean includeDisabled,
        Pageable pageable
    );

}
