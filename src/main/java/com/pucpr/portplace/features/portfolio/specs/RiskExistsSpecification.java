package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.portfolio.services.internal.RiskEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RiskExistsSpecification implements Specification<Long> {

    private RiskEntityService riskEntityService;

    @Override
    public boolean isSatisfiedBy(Long riskId) {
        return riskEntityService.existsById(riskId);
    }
    
}
