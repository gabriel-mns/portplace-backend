package com.pucpr.portplace.features.resource.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.exceptions.PositionNotFoundException;
import com.pucpr.portplace.features.resource.specs.PositionExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PositionValidationService {
    
    private PositionExistsSpecification positionExistsSpecification;

    public void validateBeforeGet(Long positionId) {
        if (!positionExistsSpecification.isSatisfiedBy(positionId)) {
            throw new PositionNotFoundException(positionId);
        }
    }

    public void validateBeforeUpdate(Long positionId) {
        validateBeforeGet(positionId);
    }

    public void validateBeforeDelete(Long positionId) {
        validateBeforeGet(positionId);
    }


}
