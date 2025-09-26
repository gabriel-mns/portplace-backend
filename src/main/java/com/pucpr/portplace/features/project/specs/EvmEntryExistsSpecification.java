package com.pucpr.portplace.features.project.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.project.services.internal.EvmEntryEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EvmEntryExistsSpecification implements Specification<Long> {

    private EvmEntryEntityService service;

    @Override
    public boolean isSatisfiedBy(Long entryId) {
        return service.existsById(entryId);
    }
    
}
