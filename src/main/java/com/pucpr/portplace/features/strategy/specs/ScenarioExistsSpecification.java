package com.pucpr.portplace.features.strategy.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.strategy.services.internal.ScenarioEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScenarioExistsSpecification implements Specification<Long> {

    private ScenarioEntityService service;

    @Override
    public boolean isSatisfiedBy(Long id) {
        
        return service.existsById(id);

    }

}
