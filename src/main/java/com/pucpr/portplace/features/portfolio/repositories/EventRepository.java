package com.pucpr.portplace.features.portfolio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.portfolio.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    
    @Query(
    """
        SELECT e FROM Event e
        WHERE (:portfolioId IS NULL OR e.portfolio.id = :portfolioId)
        AND (:includeDisabled = TRUE OR e.disabled = FALSE)
        AND (LOWER(e.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
    """
    )
    Page<Event> findByFilters(
        Long portfolioId,
        String searchQuery, 
        boolean includeDisabled,
        Pageable pageable
    );
    
    @Query(
    """
        SELECT e FROM Event e
            JOIN e.participants p
            JOIN p.stakeholder s
        WHERE (:participantId IS NULL OR s.id = :participantId)
        AND (:includeDisabled = TRUE OR e.disabled = FALSE)
        AND (LOWER(e.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
    """
    )
    Page<Event> findByParticipant(
        Long participantId, 
        String searchQuery, 
        boolean includeDisabled,
        Pageable pageable
    );

}
