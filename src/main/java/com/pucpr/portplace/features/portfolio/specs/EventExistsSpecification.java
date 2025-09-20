package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.portfolio.services.internal.EventEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EventExistsSpecification implements Specification<Long> {

    private EventEntityService eventEntityService;

    @Override
    public boolean isSatisfiedBy(Long id) {
        return eventEntityService.existsById(id);
    }
    
}
