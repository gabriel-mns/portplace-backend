package com.pucpr.portplace.features.resource.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class AllocationRequestAlreadyAllocatedException extends BusinessException {

    public AllocationRequestAlreadyAllocatedException(Long requestId) {
        super("The allocation request with ID " + requestId + " already has an allocation.", HttpStatus.CONFLICT);
    }
    
}
