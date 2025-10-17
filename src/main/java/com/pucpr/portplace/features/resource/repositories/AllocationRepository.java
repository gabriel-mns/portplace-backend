package com.pucpr.portplace.features.resource.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pucpr.portplace.features.resource.entities.Allocation;
import com.pucpr.portplace.features.resource.enums.AllocationStatusEnum;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    
    @Query("""
        SELECT al FROM Allocation al
        WHERE (al.endDate >= :startDate)
            AND (al.startDate <= :endDate)
            AND (:statuses IS NULL OR al.status IN :statuses)
            AND LOWER(al.resource.name) LIKE LOWER(CONCAT('%', :searchQuery, '%'))
            AND (:includeDisabled = TRUE OR al.disabled = false)
            AND (:resourceId IS NULL OR al.resource.id = :resourceId)
            AND (:projectId IS NULL OR al.allocationRequest.project.id = :projectId)
    """)
    Page<Allocation> findByFilters(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("statuses") List<AllocationStatusEnum> statuses,
        @Param("searchQuery") String searchQuery,
        @Param("includeDisabled") boolean includeDisabled,
        @Param("resourceId") Long resourceId,
        @Param("projectId") Long projectId,
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
            AND (:resourceId IS NULL OR r.id = :resourceId)
            AND (:projectId IS NULL OR p.id = :projectId)
    """)
    List<Allocation> findByFiltersUnpaged(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("resourceId") Long resourceId,
        @Param("projectId") Long projectId
    );

}
