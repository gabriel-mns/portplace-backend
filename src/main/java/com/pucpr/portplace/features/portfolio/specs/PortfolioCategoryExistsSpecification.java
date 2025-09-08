package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.portfolio.services.internal.PortfolioCategoryEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PortfolioCategoryExistsSpecification implements Specification<Long> {
    
    private PortfolioCategoryEntityService service;

    @Override
    public boolean isSatisfiedBy(Long categoryId) {
        return service.existsById(categoryId);
    }
    
}
