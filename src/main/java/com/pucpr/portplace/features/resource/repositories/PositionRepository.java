package com.pucpr.portplace.features.resource.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.resource.entities.Position;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;

public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("""
        SELECT p FROM Position p
        WHERE (:includeDisabled = true OR p.disabled = false)
            AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
            AND (:status IS NULL OR p.status IN :status)
        """)
    Page<Position> findByFilters(
        List<ResourceStatusEnum> status, 
        String searchQuery, 
        boolean includeDisabled,
        Pageable pageable
    );

    @Query("""
        SELECT p FROM Position p
        WHERE (:includeDisabled = true OR p.disabled = false)
            AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
            AND (:status IS NULL OR p.status IN :status)
        """)
    List<Position> findByFiltersUnpaged(
        List<ResourceStatusEnum> status, 
        String searchQuery, 
        boolean includeDisabled
    );
    
}
