package com.pucpr.portplace.features.portfolio.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.entities.EventParticipant;
import com.pucpr.portplace.features.portfolio.repositories.EventParticipantRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventParticipantEntityService {

    private EventParticipantRepository repository;

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsByEventIdAndStakeholderId(Long eventId, Long stakeholderId) {
        
        return repository.existsByEventIdAndStakeholderIdAndDisabledFalse(eventId, stakeholderId);

    }

    public EventParticipant getById(Long id) {
        return repository.findById(id).get();
    }

}
