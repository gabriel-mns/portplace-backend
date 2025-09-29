package com.pucpr.portplace.features.resource.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
        SELECT a 
        FROM Allocation a
            JOIN FETCH a.allocationRequest ar
            JOIN FETCH ar.project p
            JOIN FETCH a.resource r
        WHERE a.startDate <= :endDate
            AND a.endDate >= :startDate
    """)
    List<Allocation> findByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

}
