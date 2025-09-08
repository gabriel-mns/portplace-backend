 package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.portfolio.services.internal.PortfolioEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PortfolioExistsSpecification implements Specification<Long>{

    private PortfolioEntityService portfolioEntityService;

    @Override
    public boolean isSatisfiedBy(Long candidate) {
        return portfolioEntityService.existsById(candidate);
    }
    
}
