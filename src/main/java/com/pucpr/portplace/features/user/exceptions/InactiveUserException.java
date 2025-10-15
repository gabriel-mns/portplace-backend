package com.pucpr.portplace.features.user.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class InactiveUserException extends BusinessException {

    public InactiveUserException(String email) {
        super("User with email " + email + " is inactive", HttpStatus.FORBIDDEN);
    }
    
}
