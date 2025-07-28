package com.pucpr.portplace.authentication.features.ahp.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.authentication.core.exception.BusinessException;

public class SameCriteriaException extends BusinessException {

    public SameCriteriaException(Long comparedCriterionId, Long referenceCriterionId) {
        super("Compared criterion and reference criterion must be different. Compared ID: " + comparedCriterionId + ", Reference ID: " + referenceCriterionId, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public SameCriteriaException(String message) {
        super(message);
    }
    
}
