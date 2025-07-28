package com.pucpr.portplace.authentication.features.ahp.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.authentication.core.exception.BusinessException;

public class NotAllProjectsEvaluatedException extends BusinessException{

    public NotAllProjectsEvaluatedException() {
        super("Not all projects have been evaluated. Please ensure all projects are evaluated before proceeding.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public NotAllProjectsEvaluatedException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
}
