package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.BiSpecification;
import com.pucpr.portplace.features.portfolio.services.internal.EventParticipantEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ParticipantNotConflictingSpecification implements BiSpecification<Long, Long> {

    private EventParticipantEntityService participantService;

    @Override
    public boolean isSatisfiedBy(Long eventId, Long stakeholderId) {

        return !participantService.existsByEventIdAndStakeholderId(eventId, stakeholderId);

    }

}
