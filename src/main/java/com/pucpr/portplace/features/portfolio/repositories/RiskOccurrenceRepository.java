package com.pucpr.portplace.features.portfolio.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.portfolio.entities.RiskOccurrence;
import com.pucpr.portplace.features.portfolio.enums.RiskOccurrenceStatusEnum;

public interface RiskOccurrenceRepository extends JpaRepository<RiskOccurrence, Long> {

    @Query(
        """
        SELECT o FROM RiskOccurrence o 
        WHERE o.risk.id = :riskId
            AND (:status IS NULL OR o.status IN :status)
            AND (:includeDisabled = true OR o.disabled = false)
            AND (LOWER(o.description) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        """
    )
    Page<RiskOccurrence> findByFilters(
        Long riskId, 
        List<RiskOccurrenceStatusEnum> status, 
        boolean includeDisabled,
        String searchQuery, 
        Pageable pageable
    );

}
