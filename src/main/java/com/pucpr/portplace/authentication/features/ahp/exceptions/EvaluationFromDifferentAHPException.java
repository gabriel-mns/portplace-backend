package com.pucpr.portplace.authentication.features.ahp.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.authentication.core.exception.BusinessException;

public class EvaluationFromDifferentAHPException extends BusinessException {

    public EvaluationFromDifferentAHPException(long evaluationId, long ahpId) {
        super("Evaluation does not belong to AHP. Evaluation ID: " + evaluationId + ", AHP ID: " + ahpId, HttpStatus.BAD_REQUEST);
    }

    public EvaluationFromDifferentAHPException(String message) {
        super(message);
    }
    
}
