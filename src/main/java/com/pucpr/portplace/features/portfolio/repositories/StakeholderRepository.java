package com.pucpr.portplace.features.portfolio.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.portfolio.entities.Stakeholder;

public interface StakeholderRepository extends JpaRepository<Stakeholder, Long> {

    @Query("""
         SELECT s FROM Stakeholder s
         WHERE s.portfolio.id = :portfolioId       
         AND (s.name LIKE %:searchQuery%)
         AND (s.disabled = :includeDisabled OR s.disabled = false)
    """)
    Page<Stakeholder> findByFilters(
        Long portfolioId, 
        String searchQuery, 
        boolean includeDisabled, 
        Pageable pageable
    );


    @Query("""
        SELECT s FROM Stakeholder s
        WHERE s.portfolio.id = :portfolioId
        AND (:searchQuery IS NULL OR s.name LIKE %:searchQuery%)
        AND (s.disabled = :includeDisabled OR s.disabled = false)
        AND s.id NOT IN (
            SELECT ep.stakeholder.id FROM EventParticipant ep
            WHERE ep.event.id = :eventId
            AND ep.disabled = false
        )
    """)
    List<Stakeholder> findAvailableForEvent(
        Long portfolioId, 
        Long eventId, 
        String searchQuery, 
        boolean includeDisabled
    );

}
