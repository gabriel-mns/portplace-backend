package com.pucpr.portplace.features.strategy.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.strategy.services.internal.StrategyEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StrategyExistsSpecification implements Specification<Long> {

    private StrategyEntityService service;

    @Override
    public boolean isSatisfiedBy(Long strategyId) {
        
        return service.existsById(strategyId);

    }
    
}
