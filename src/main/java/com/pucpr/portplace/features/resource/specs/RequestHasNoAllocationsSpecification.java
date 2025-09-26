package com.pucpr.portplace.features.resource.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.resource.entities.AllocationRequest;
import com.pucpr.portplace.features.resource.services.internal.AllocationRequestEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RequestHasNoAllocationsSpecification implements Specification<Long> {

    private AllocationRequestEntityService service;

    @Override
    public boolean isSatisfiedBy(Long requestId) {
        
        AllocationRequest request = service.findById(requestId);

        return request.getAllocation() == null;

    }
    
}
