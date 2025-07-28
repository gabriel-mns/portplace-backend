package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.ahp.services.internal.AHPEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AHPExistsSpecification implements Specification<Long> {

    private AHPEntityService ahpService;

    @Override
    public boolean isSatisfiedBy(Long ahpId) {
        return ahpService.existsById(ahpId);
    }
    
}
