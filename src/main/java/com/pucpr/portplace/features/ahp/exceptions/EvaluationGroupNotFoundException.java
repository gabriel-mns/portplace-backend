package com.pucpr.portplace.features.ahp.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class EvaluationGroupNotFoundException extends EntityNotFoundException {

    public EvaluationGroupNotFoundException(Long evaluationGroupId) {
        super("Evaluation Group not found with ID: " + evaluationGroupId);
    }

    public EvaluationGroupNotFoundException(String message) {
        super(message);
    }
    
}
