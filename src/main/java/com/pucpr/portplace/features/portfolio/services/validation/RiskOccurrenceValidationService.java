package com.pucpr.portplace.features.portfolio.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.exceptions.RiskNotFoundException;
import com.pucpr.portplace.features.portfolio.exceptions.RiskOccurrenceNotFoundException;
import com.pucpr.portplace.features.portfolio.specs.RiskExistsSpecification;
import com.pucpr.portplace.features.portfolio.specs.RiskOccurrenceExistsSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RiskOccurrenceValidationService {
    
    private RiskExistsSpecification riskExistsSpecification;
    private RiskOccurrenceExistsSpecification riskOccurrenceExistsSpecification;

    public void validateBeforeCreate(
        Long riskId
    ) {
        if (!riskExistsSpecification.isSatisfiedBy(riskId)) {
            throw new RiskNotFoundException(riskId);
        }
    }

    public void validateBeforeGet(
        Long occurrenceId
    ) {
        if (!riskOccurrenceExistsSpecification.isSatisfiedBy(occurrenceId)) {
            throw new RiskOccurrenceNotFoundException(occurrenceId);
        }
    }

    public void validateBeforeGetAll(
        Long riskId
    ) {
        if (!riskExistsSpecification.isSatisfiedBy(riskId)) {
            throw new RiskNotFoundException(riskId);
        }
    }

    public void validateBeforeUpdate(
        Long occurrenceId
    ) {
        validateBeforeGet(occurrenceId);
    }

    public void validateBeforeDisable(
        Long occurrenceId
    ) {
        validateBeforeGet(occurrenceId);
    }

}
