package com.pucpr.portplace.features.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.portfolio.entities.EventParticipant;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    
}
