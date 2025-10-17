package com.pucpr.portplace.features.resource.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.exceptions.PositionNotFoundException;
import com.pucpr.portplace.features.resource.exceptions.ResourceNotFoundException;
import com.pucpr.portplace.features.resource.specs.PositionExistsSpecification;
import com.pucpr.portplace.features.resource.specs.ResourceExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResourceValidationService {
    
    private final ResourceExistsSpecification resourceExistsSpecification;
    private final PositionExistsSpecification positionExistsSpecification;

    public void validateBeforeCreate(
        Long positionId
    ) {
        if (!positionExistsSpecification.isSatisfiedBy(positionId)) {
            throw new PositionNotFoundException(positionId);
        }
    }

    public void validateBeforeGet(
        Long resourceId
    ) {
        if (!resourceExistsSpecification.isSatisfiedBy(resourceId)) {
            throw new ResourceNotFoundException(resourceId);
        }
    }

    public void validateBeforeUpdate(
        Long resourceId,
        Long positionId
    ) {
        validateBeforeGet(resourceId);

        if (!positionExistsSpecification.isSatisfiedBy(positionId)) {
            throw new PositionNotFoundException(positionId);
        }
    }

    public void validateBeforeDisable(
        Long resourceId
    ) {
        validateBeforeGet(resourceId);
    }
    

}
