package com.pucpr.portplace.features.portfolio.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.exceptions.PortfolioNotFoundException;
import com.pucpr.portplace.features.portfolio.specs.PortfolioExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioValidationService {

    private PortfolioExistsSpecification portfolioExistsSpecification;

    public void validateBeforeUpdate(long portfolioId) {
        
        if(!portfolioExistsSpecification.isSatisfiedBy(portfolioId)){
            throw new PortfolioNotFoundException(portfolioId);
        }
    }

    public void validateBeforeGet(Long portfolioId) {

        if(!portfolioExistsSpecification.isSatisfiedBy(portfolioId)){
            throw new PortfolioNotFoundException(portfolioId);
        }

    }

}
