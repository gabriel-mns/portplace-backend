package com.pucpr.portplace.features.portfolio.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.exceptions.PortfolioCategoryNotFoundException;
import com.pucpr.portplace.features.portfolio.exceptions.PortfolioNotFoundException;
import com.pucpr.portplace.features.portfolio.specs.PortfolioCategoryExistsSpecification;
import com.pucpr.portplace.features.portfolio.specs.PortfolioExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioCategoryValidationService {
    
    private PortfolioExistsSpecification portfolioExistsSpec;
    private PortfolioCategoryExistsSpecification categoryExistsSpec;

    public void validateBeforeCreate(
        Long portfolioId
    ) {

        if (!portfolioExistsSpec.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }

    }

    public void validateBeforeGet(
        Long categoryId
    ) {

        if (!categoryExistsSpec.isSatisfiedBy(categoryId)) {
            throw new PortfolioCategoryNotFoundException(categoryId);
        }

    }

    public void validateBeforeUpdate(
        Long categoryId
    ) {

        validateBeforeGet(categoryId);

    }

    public void validateBeforeGetAll(
        Long portfolioId
    ) {

        if (!portfolioExistsSpec.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }

    }

    public void validateBeforeDelete(
        Long categoryId
    ) {

        validateBeforeGet(categoryId);

    }

}
