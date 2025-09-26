package com.pucpr.portplace.features.portfolio.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.exceptions.PortfolioNotFoundException;
import com.pucpr.portplace.features.portfolio.exceptions.StakeholderNotFoundException;
import com.pucpr.portplace.features.portfolio.specs.PortfolioExistsSpecification;
import com.pucpr.portplace.features.portfolio.specs.StakeholderExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StakeholderValidationService {
    
    private StakeholderExistsSpecification stakeholderExistsSpec;
    private PortfolioExistsSpecification portfolioExistsSpec;

    public void validateBeforeCreate(
        Long portfolioId
    ) {

        if (!portfolioExistsSpec.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }

    }
    
    public void validateBeforeGet(
        Long stakeholderId
    ) {
        
        if (!stakeholderExistsSpec.isSatisfiedBy(stakeholderId)) {
            throw new StakeholderNotFoundException(stakeholderId);
        }
        
    }
    
    public void validateBeforeDelete(
        Long stakeholderId
    ) {

        validateBeforeGet(stakeholderId);

    }
        
    public void validateBeforeGetAll(
            Long portfolioId
    ) {

        if (!portfolioExistsSpec.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }

    }

    public void validateBeforeUpdate(Long stakeholderId) {

        validateBeforeGet(stakeholderId);
        
    }

}
