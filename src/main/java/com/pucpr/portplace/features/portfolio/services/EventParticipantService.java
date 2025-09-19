package com.pucpr.portplace.features.portfolio.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.EventParticipant;
import com.pucpr.portplace.features.portfolio.entities.Stakeholder;
import com.pucpr.portplace.features.portfolio.mappers.EventParticipantMapper;
import com.pucpr.portplace.features.portfolio.repositories.EventParticipantRepository;
import com.pucpr.portplace.features.portfolio.services.internal.StakeholderEntityService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventParticipantService {
    
    private EventParticipantRepository repository;
    private EventParticipantMapper mapper;
    private StakeholderEntityService stakeholderService;

    //CREATE
    public EventParticipantReadDTO addParticipant(
        Long eventId,
        EventParticipantCreateDTO dto
    ) {
        
        //TODO: validate if event exists

        dto.setEventId(eventId);

        EventParticipant entity = mapper.toEntity(dto);

        entity = repository.save(entity);

        return mapper.toReadDTO(entity);

    }
    
    //UPDATE
    public EventParticipantReadDTO updateParticipant(
        Long participantId,
        EventParticipantUpdateDTO dto
    ) {
        
        //TODO: validate if participant exists
        //TODO: Validate if new stakeholder is not already a participant

        EventParticipant participant = repository.findById(participantId).get();

        mapper.updateFromDTO(dto, participant);

        Stakeholder stakeholder = stakeholderService.getStakeholderById(dto.getStakeholderId());

        participant.setStakeholder(stakeholder);

        participant = repository.save(participant);

        return mapper.toReadDTO(participant);

    }

    //DELETE
    public void disableParticipant(
        Long participantId
    ) {
        
        //TODO: validate if participant exists
        EventParticipant participant = repository.findById(participantId).get();

        participant.setDisabled(true);

        repository.save(participant);
    }

    public void deleteParticipant(
        Long participantId
    ) {
    
        repository.deleteById(participantId);

    }

    //READ
    public EventParticipantReadDTO getParticipantById(
        Long participantId
    ) {

        EventParticipant participant = repository.findById(participantId).get();

        return mapper.toReadDTO(participant);

    }

    public Page<EventParticipantReadDTO> getAllParticipants(
        Long eventId,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        Page<EventParticipant> participants = repository.findByFilters(
            eventId, 
            searchQuery, 
            includeDisabled, 
            pageable
        );

        return participants.map(mapper::toReadDTO);

    }

}
