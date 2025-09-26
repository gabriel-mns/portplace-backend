package com.pucpr.portplace.features.portfolio.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.EventParticipant;
import com.pucpr.portplace.features.portfolio.exceptions.EventNotFoundException;
import com.pucpr.portplace.features.portfolio.exceptions.ParticipantAlreadyRegisteredException;
import com.pucpr.portplace.features.portfolio.exceptions.ParticipantNotFoundException;
import com.pucpr.portplace.features.portfolio.exceptions.StakeholderNotFoundException;
import com.pucpr.portplace.features.portfolio.services.internal.EventParticipantEntityService;
import com.pucpr.portplace.features.portfolio.specs.EventExistsSpecification;
import com.pucpr.portplace.features.portfolio.specs.EventParticipantExistsSpecification;
import com.pucpr.portplace.features.portfolio.specs.ParticipantNotConflictingSpecification;
import com.pucpr.portplace.features.portfolio.specs.StakeholderExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventParticipantValidationService {
    
    private EventExistsSpecification eventExistsSpec;
    private StakeholderExistsSpecification stakeholderExistsSpec;
    private EventParticipantExistsSpecification participantExistsSpec;
    private ParticipantNotConflictingSpecification participantNotConflictingSpec;
    private EventParticipantEntityService participantService;

    public void validateBeforeCreate(
        EventParticipantCreateDTO dto
    ) {

        if (!eventExistsSpec.isSatisfiedBy(dto.getEventId())) {
            throw new EventNotFoundException(dto.getEventId());
        }

        if (!stakeholderExistsSpec.isSatisfiedBy(dto.getStakeholderId())) {
            throw new StakeholderNotFoundException(dto.getStakeholderId());
        }

        if (!participantNotConflictingSpec.isSatisfiedBy(dto.getEventId(), dto.getStakeholderId())) {
            throw new ParticipantAlreadyRegisteredException(dto.getStakeholderId(), dto.getEventId());
        }

    }

    public void validateBeforeGet(Long participantId) {

        if (!participantExistsSpec.isSatisfiedBy(participantId)) {
            throw new ParticipantNotFoundException(participantId);
        }

    }

    public void validateBeforeUpdate(Long participantId, EventParticipantUpdateDTO dto) {

        validateBeforeGet(participantId);

        long newStakeholderId = dto.getStakeholderId();

        if (!stakeholderExistsSpec.isSatisfiedBy(newStakeholderId)) {
            throw new StakeholderNotFoundException(newStakeholderId);
        }

        EventParticipant participant = participantService.getById(participantId);
        boolean stakeholderChanged = participant.getStakeholder().getId() != newStakeholderId;

        if (stakeholderChanged && !participantNotConflictingSpec.isSatisfiedBy(participant.getEvent().getId(), newStakeholderId)) {
            throw new ParticipantAlreadyRegisteredException(newStakeholderId, participant.getEvent().getId());
        }

    }

    public void validateBeforeDisable(Long participantId) {

        validateBeforeGet(participantId);

    }

    public void validateBeforeGetAll(Long eventId) {

        if (!eventExistsSpec.isSatisfiedBy(eventId)) {
            throw new EventNotFoundException(eventId);
        }

    }

}
