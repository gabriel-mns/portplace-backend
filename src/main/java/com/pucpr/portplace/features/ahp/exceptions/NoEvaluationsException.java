package com.pucpr.portplace.features.ahp.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class NoEvaluationsException extends BusinessException {
    
    public NoEvaluationsException(long evaluationGroupId) {
        super("No evaluations found for evaluation group with ID: " + evaluationGroupId + ".", HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
}
