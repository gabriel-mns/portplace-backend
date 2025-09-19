package com.pucpr.portplace.features.portfolio.services.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.event.EventReadDTO;
import com.pucpr.portplace.features.portfolio.mappers.EventMapper;
import com.pucpr.portplace.features.portfolio.repositories.EventRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventEntityService {
    
    private EventRepository repository;
    private EventMapper mapper;

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public Page<EventReadDTO> findByStakeholderId(
        Long stakeholderId,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {
        var events = repository.findByParticipant(
            stakeholderId,
            searchQuery,
            includeDisabled, 
            pageable
        );

        return events.map(mapper::toReadDTO);
    }

}
