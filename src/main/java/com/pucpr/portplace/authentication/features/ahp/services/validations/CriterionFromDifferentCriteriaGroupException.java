package com.pucpr.portplace.authentication.features.ahp.services.validations;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.authentication.core.exception.BusinessException;

public class CriterionFromDifferentCriteriaGroupException extends BusinessException{
    
    public CriterionFromDifferentCriteriaGroupException(long criterionId, long ahpId) {
        super("Criterion does not belong to the same criteria group as AHP. Criterion Id: " + criterionId + ", AHP Id: " + ahpId, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    public CriterionFromDifferentCriteriaGroupException(String message) {
        super(message);
    }

}
