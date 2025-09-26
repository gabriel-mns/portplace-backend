package com.pucpr.portplace.features.portfolio.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class RequestedOwnerIsNotPMOException extends BusinessException {
    
    public RequestedOwnerIsNotPMOException(Long ownerId) {
        super("The requested owner with id " + ownerId + " is not a PMO or PMO_ADM user.", HttpStatus.UNAUTHORIZED);
    }

}
