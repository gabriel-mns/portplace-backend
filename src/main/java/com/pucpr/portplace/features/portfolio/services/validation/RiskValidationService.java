package com.pucpr.portplace.features.portfolio.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.exceptions.PortfolioNotFoundException;
import com.pucpr.portplace.features.portfolio.exceptions.RiskNotFoundException;
import com.pucpr.portplace.features.portfolio.specs.PortfolioExistsSpecification;
import com.pucpr.portplace.features.portfolio.specs.RiskExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RiskValidationService {
    
    private PortfolioExistsSpecification portfolioExistsSpecification;
    private RiskExistsSpecification riskExistsSpecification;

    public void validateBeforeGet(Long riskId) {
        if(!riskExistsSpecification.isSatisfiedBy(riskId)) {
            throw new RiskNotFoundException(riskId);
        }
    }

    public void validateBeforeGetAll(Long portfolioId) {
        if (!portfolioExistsSpecification.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }
    }

    public void validateBeforeCreate(Long portfolioId) {
        if (!portfolioExistsSpecification.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }
    }

    public void validateBeforeUpdate(Long riskId) {
        validateBeforeGet(riskId);
    }

    public void validateBeforeDisable(Long riskId) {
        validateBeforeGet(riskId);
    }

}
