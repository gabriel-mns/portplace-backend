package com.pucpr.portplace.authentication.features.ahp.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class EvaluationNotFoundException extends EntityNotFoundException {

    public EvaluationNotFoundException(long evaluationId) {
        super("Evaluation not found with ID: " + evaluationId);
    }

    public EvaluationNotFoundException(String message) {
        super(message);
    }
    
}
