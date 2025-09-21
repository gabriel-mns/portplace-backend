package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.portfolio.services.internal.RiskOccurrenceEntityService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RiskOccurrenceExistsSpecification implements Specification<Long> {

    private RiskOccurrenceEntityService riskService;

    @Override
    public boolean isSatisfiedBy(Long occurrenceId) {
        return riskService.existsById(occurrenceId);
    }
    
}
