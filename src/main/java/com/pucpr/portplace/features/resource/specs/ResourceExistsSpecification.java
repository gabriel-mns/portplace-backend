package com.pucpr.portplace.features.resource.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.resource.services.internal.ResourceEntityService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ResourceExistsSpecification implements Specification<Long> {

    private final ResourceEntityService resourceEntityService;

    @Override
    public boolean isSatisfiedBy(Long candidate) {
        return resourceEntityService.existsById(candidate);
    }
    
}
