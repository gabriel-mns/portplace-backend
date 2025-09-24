package com.pucpr.portplace.features.resource.repositories;

import com.pucpr.portplace.features.resource.entities.Resource;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query(
        """
        SELECT r FROM Resource r
        WHERE (:includeDisabled = true OR r.disabled = false)
            AND (LOWER(r.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
            AND (:status IS NULL OR r.status IN :status)
        """
    )
    Page<Resource> findByFilters(
        List<ResourceStatusEnum> status,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    );


}
