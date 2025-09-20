package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.portfolio.services.internal.EventParticipantEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EventParticipantExistsSpecification implements Specification<Long> {

    private EventParticipantEntityService participantService;

    @Override
    public boolean isSatisfiedBy(Long id) {
        return participantService.existsById(id);
    }
    
}
