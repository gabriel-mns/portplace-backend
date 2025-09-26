package com.pucpr.portplace.features.resource.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.resource.entities.Allocation;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    
    @Query("""
        SELECT ar FROM Allocation ar
        WHERE LOWER(ar.resource.name) LIKE LOWER(CONCAT('%', :searchQuery, '%'))
            AND (:includeDisabled = TRUE OR ar.disabled = false)
    """)
    Page<Allocation> findByFilters(
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    );

}
