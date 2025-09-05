 package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.portfolio.repositories.PortfolioRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PortfolioExistsSpecification implements Specification<Long>{

    private PortfolioRepository portfolioRepository;

    @Override
    public boolean isSatisfiedBy(Long candidate) {
        return portfolioRepository.existsById(candidate);
    }
    
}
