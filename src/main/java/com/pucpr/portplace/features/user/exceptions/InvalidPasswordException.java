package com.pucpr.portplace.features.user.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {

    public InvalidPasswordException() {
        super("The provided password does not meet the required criteria (5 characters minimum).", HttpStatus.BAD_REQUEST);
    }
    
}
