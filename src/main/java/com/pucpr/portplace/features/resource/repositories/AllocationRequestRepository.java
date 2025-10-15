package com.pucpr.portplace.features.resource.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.resource.entities.AllocationRequest;
import com.pucpr.portplace.features.resource.enums.AllocationRequestStatusEnum;

public interface AllocationRequestRepository extends JpaRepository<AllocationRequest, Long> {

    @Query("""
        SELECT ar FROM AllocationRequest ar
        LEFT JOIN ar.allocation a
        LEFT JOIN a.resource r
        WHERE (:status IS NULL OR ar.status IN :status)
        AND LOWER(ar.position.name) LIKE LOWER(CONCAT('%', :searchQuery, '%'))
        AND (:resourceId IS NULL OR r.id = :resourceId)
        AND (:includeDisabled = TRUE OR ar.disabled = false)
    """)
    Page<AllocationRequest> findByFilters(
        Long resourceId,
        List<AllocationRequestStatusEnum> status,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    );

}
