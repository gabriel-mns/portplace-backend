package com.pucpr.portplace.features.ahp.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class NotAllCriteriaComparedException extends BusinessException{
    
    public NotAllCriteriaComparedException() {
        super("Not all criteria have been compared. Please ensure all criteria are compared before proceeding.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public NotAllCriteriaComparedException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
