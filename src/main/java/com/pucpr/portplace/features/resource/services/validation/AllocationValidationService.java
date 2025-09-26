package com.pucpr.portplace.features.resource.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.resource.dtos.allocation.AllocationCreateDTO;
import com.pucpr.portplace.features.resource.dtos.allocation.AllocationUpdateDTO;
import com.pucpr.portplace.features.resource.exceptions.AllocationNotFoundException;
import com.pucpr.portplace.features.resource.exceptions.AllocationRequestAlreadyAllocatedException;
import com.pucpr.portplace.features.resource.exceptions.AllocationRequestNotFoundException;
import com.pucpr.portplace.features.resource.exceptions.ResourceNotFoundException;
import com.pucpr.portplace.features.resource.specs.AllocationExistsSpecification;
import com.pucpr.portplace.features.resource.specs.AllocationRequestExistsSpecification;
import com.pucpr.portplace.features.resource.specs.RequestHasNoAllocationsSpecification;
import com.pucpr.portplace.features.resource.specs.ResourceExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AllocationValidationService {
    
    private AllocationExistsSpecification allocationExistsSpecification;
    private RequestHasNoAllocationsSpecification requestHasNoAllocationsSpecification;
    private ResourceExistsSpecification resourceExistsSpecification;
    private AllocationRequestExistsSpecification allocationRequestExistsSpecification;

    public void validateBeforeCreate(
        AllocationCreateDTO dto
    ) {

        Long requestId = dto.getAllocationRequestId();
        Long resourceId = dto.getResourceId();

        if (!allocationRequestExistsSpecification.isSatisfiedBy(requestId)) {
            throw new AllocationRequestNotFoundException(resourceId);
        }

        if (!requestHasNoAllocationsSpecification.isSatisfiedBy(requestId)) {
            throw new AllocationRequestAlreadyAllocatedException(requestId);
        }

        if (!resourceExistsSpecification.isSatisfiedBy(resourceId)) {
            throw new ResourceNotFoundException(resourceId);
        }

    }

    public void validateBeforeGet(Long allocationId) {
        if (!allocationExistsSpecification.isSatisfiedBy(allocationId)) {
            throw new AllocationNotFoundException(allocationId);
        }
    }

    public void validateBeforeDelete(Long allocationId) {
        validateBeforeGet(allocationId);
    }

    public void validateBeforeUpdate(
        Long allocationId, 
        AllocationUpdateDTO dto
    ) {
        
        validateBeforeGet(allocationId);

        Long resourceId = dto.getResourceId();

        if (!resourceExistsSpecification.isSatisfiedBy(resourceId)) {
            throw new ResourceNotFoundException(resourceId);
        }

    }

}
