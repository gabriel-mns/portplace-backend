package com.pucpr.portplace.features.resource.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.resource.services.internal.PositionEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PositionExistsSpecification implements Specification<Long> {

    private PositionEntityService positionEntityService;

    @Override
    public boolean isSatisfiedBy(Long positionId) {
            return positionEntityService.existsById(positionId);
    }
    
}
