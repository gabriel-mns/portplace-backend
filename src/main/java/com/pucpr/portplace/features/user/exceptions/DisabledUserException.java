package com.pucpr.portplace.features.user.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class DisabledUserException extends BusinessException {

    public DisabledUserException(String email) {
        super("User with email " + email + " is disabled", HttpStatus.FORBIDDEN);
    }
    
}
