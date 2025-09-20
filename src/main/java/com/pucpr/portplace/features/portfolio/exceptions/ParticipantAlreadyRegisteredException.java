package com.pucpr.portplace.features.portfolio.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class ParticipantAlreadyRegisteredException extends BusinessException {

    public ParticipantAlreadyRegisteredException(Long stakeholderId, Long eventId) {
        super("Stakeholder with ID " + stakeholderId + " is already registered for event with ID " + eventId, HttpStatus.CONFLICT);
    }

}
