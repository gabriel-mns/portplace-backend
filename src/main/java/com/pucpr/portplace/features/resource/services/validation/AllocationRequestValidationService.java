package com.pucpr.portplace.features.resource.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.core.validation.specs.EntityIsEnabledSpecification;
import com.pucpr.portplace.features.resource.entities.Position;
import com.pucpr.portplace.features.resource.exceptions.AllocationRequestNotFoundException;
import com.pucpr.portplace.features.resource.exceptions.PositionNotFoundException;
import com.pucpr.portplace.features.resource.services.internal.PositionEntityService;
import com.pucpr.portplace.features.resource.specs.AllocationRequestExistsSpecification;
import com.pucpr.portplace.features.resource.specs.PositionExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AllocationRequestValidationService {
    
    private final EntityIsEnabledSpecification entityIsEnabledSpecification;
    private final PositionExistsSpecification positionExistsSpecification;
    private final AllocationRequestExistsSpecification allocationRequestExistsSpecification;
    private final PositionEntityService positionEntityService;

    public void validateBeforeCreate(
        Long positionId
    ) {

        if(!positionExistsSpecification.isSatisfiedBy(positionId)) {
            throw new PositionNotFoundException(positionId);
        }

        Position position = positionEntityService.findById(positionId);

        if(!entityIsEnabledSpecification.isSatisfiedBy(position)) {
            throw new PositionNotFoundException(positionId);
        }

    }

    public void validateBeforeGet(
        Long allocationRequestId
    ){

        if(!allocationRequestExistsSpecification.isSatisfiedBy(allocationRequestId)) {
            throw new AllocationRequestNotFoundException(allocationRequestId);
        }

    }

    public void validateBeforeUpdate(
        Long allocationRequestId, 
        Long positionId
    ){

        validateBeforeGet(allocationRequestId);

        if(!positionExistsSpecification.isSatisfiedBy(positionId)) {
            throw new PositionNotFoundException(positionId);
        }

        Position position = positionEntityService.findById(positionId);

        if(!entityIsEnabledSpecification.isSatisfiedBy(position)) {
            throw new PositionNotFoundException(positionId);
        }

    }

    public void validateBeforeDisable(
        Long allocationRequestId
    ){

        validateBeforeGet(allocationRequestId);

    }

}
