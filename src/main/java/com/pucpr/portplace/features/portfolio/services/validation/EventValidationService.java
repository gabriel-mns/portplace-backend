package com.pucpr.portplace.features.portfolio.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.exceptions.EventNotFoundException;
import com.pucpr.portplace.features.portfolio.exceptions.PortfolioNotFoundException;
import com.pucpr.portplace.features.portfolio.exceptions.StakeholderNotFoundException;
import com.pucpr.portplace.features.portfolio.specs.EventExistsSpecification;
import com.pucpr.portplace.features.portfolio.specs.PortfolioExistsSpecification;
import com.pucpr.portplace.features.portfolio.specs.StakeholderExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventValidationService {
    
    private PortfolioExistsSpecification portfolioExistsSpec;
    private EventExistsSpecification eventExistsSpec;
    private StakeholderExistsSpecification stakeholderExistsSpec;

    public void validateBeforeCreate(
        long portfolioId
    ) {

        if(!portfolioExistsSpec.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }

    }

    public void validateBeforeGet(
        long eventId
    ) {

        if(!eventExistsSpec.isSatisfiedBy(eventId)) {
            throw new EventNotFoundException(eventId);
        }

    }

    public void validateBeforeGetAll(
        long portfolioId
    ) {

        if(!portfolioExistsSpec.isSatisfiedBy(portfolioId)) {
            throw new PortfolioNotFoundException(portfolioId);
        }

    }

    public void validateBeforeGetByStakeholder(
        long stakeholderId
    ) {

        if(!stakeholderExistsSpec.isSatisfiedBy(stakeholderId)) {
            throw new StakeholderNotFoundException(stakeholderId);
        }

    }

    public void validateBeforeUpdate(
        long eventId
    ) {

        validateBeforeGet(eventId);

    }

    public void validateBeforeDisable(
        long eventId
    ) {

        validateBeforeGet(eventId);

    }

}
