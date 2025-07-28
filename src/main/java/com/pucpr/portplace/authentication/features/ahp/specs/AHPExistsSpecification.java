package com.pucpr.portplace.authentication.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.validation.specs.Specification;
import com.pucpr.portplace.authentication.features.ahp.services.internal.AHPEntityService;

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
