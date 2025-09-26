package com.pucpr.portplace.features.portfolio.services.validation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.exceptions.PortfolioNotFoundException;
import com.pucpr.portplace.features.portfolio.exceptions.RequestedOwnerIsNotPMOException;
import com.pucpr.portplace.features.portfolio.specs.OwnerIsPMOSpecification;
import com.pucpr.portplace.features.portfolio.specs.PortfolioExistsSpecification;
import com.pucpr.portplace.features.user.exceptions.UserNotFoundException;
import com.pucpr.portplace.features.user.specs.UserExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioOwnerValidationService {
    
    private UserExistsSpecification userExistsSpecification;
    private OwnerIsPMOSpecification ownerIsPMOSpecification;
    private PortfolioExistsSpecification portfolioExistsSpecification;

    public void validateBeforeCreate(List<Long> ownerIds, Long portfolioId) {

        if (!portfolioExistsSpecification.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }

        for (Long ownerId : ownerIds) {
            
            if (!userExistsSpecification.isSatisfiedBy(ownerId)) {
                throw new UserNotFoundException(ownerId);
            }

            if (!ownerIsPMOSpecification.isSatisfiedBy(ownerId)) {
                throw new RequestedOwnerIsNotPMOException(ownerId);
            }

        }

    }

    public void validateBeforeGet(Long portfolioId) {

        if (!portfolioExistsSpecification.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }

    }

}
