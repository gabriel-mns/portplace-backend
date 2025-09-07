package com.pucpr.portplace.features.project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.project.entities.EvmEntry;

public interface EvmEntryRepository extends JpaRepository<EvmEntry, Long> {


    @Query("""
        SELECT e 
        FROM EvmEntry e 
        WHERE e.project.id = :projectId
        AND (:includeDisabled = true OR e.disabled = false)
    """)
    Page<EvmEntry> findByFilters(
        long projectId,
        boolean includeDisabled,
        Pageable pageable
    );
}
