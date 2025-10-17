package com.pucpr.portplace.features.resource.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.resource.services.internal.AllocationEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllocationExistsSpecification implements Specification<Long> {

    private AllocationEntityService service;

    @Override
    public boolean isSatisfiedBy(Long allocationId) {
        return service.existsById(allocationId);
    }
    
}
