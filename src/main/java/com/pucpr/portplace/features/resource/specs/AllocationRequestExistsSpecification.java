package com.pucpr.portplace.features.resource.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.resource.services.internal.AllocationRequestEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllocationRequestExistsSpecification implements Specification<Long> {

    private AllocationRequestEntityService service;

    @Override
    public boolean isSatisfiedBy(Long allocationRequestId) {
        return service.existsById(allocationRequestId);
    }
    
}
