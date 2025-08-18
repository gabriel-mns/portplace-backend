package com.pucpr.portplace.features.ahp.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class EvaluationFromDifferentEvaluationGroupException extends BusinessException {

    public EvaluationFromDifferentEvaluationGroupException(long evaluationId, long evaluationGroupId) {
        super("Evaluation does not belong to Evaluation Group. Evaluation ID: " + evaluationId + ", Evaluation Group ID: " + evaluationGroupId, HttpStatus.BAD_REQUEST);
    }

    public EvaluationFromDifferentEvaluationGroupException(String message) {
        super(message);
    }
    
}
