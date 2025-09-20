package com.pucpr.portplace.features.portfolio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.portfolio.entities.EventParticipant;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {

    @Query(
        """
        SELECT ep FROM EventParticipant ep
            JOIN ep.stakeholder s
        WHERE ep.event.id = :eventId
        AND (:includeDisabled = true OR ep.disabled = false)
        AND (LOWER(s.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        """
    )
    Page<EventParticipant> findByFilters(
        Long eventId, 
        String searchQuery, 
        boolean includeDisabled, 
        Pageable pageable
    );

    boolean existsByEventIdAndStakeholderId(Long eventId, Long stakeholderId);
    
}
