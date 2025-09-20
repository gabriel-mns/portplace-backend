package com.pucpr.portplace.features.portfolio.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ParticipantNotFoundException extends EntityNotFoundException {
    
    public ParticipantNotFoundException(Long id) {
        super("Participant could not be found with ID " + id);
    }

}
