package com.pucpr.portplace.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private HttpStatus status;

    public BusinessException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST; // Default status for business exceptions
    }

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    
}
