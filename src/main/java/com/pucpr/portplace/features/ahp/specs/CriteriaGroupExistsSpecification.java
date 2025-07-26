package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.ahp.services.internal.CriteriaGroupEntityService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CriteriaGroupExistsSpecification implements Specification<Long> {

    private CriteriaGroupEntityService service;

    @Override
    public boolean isSatisfiedBy(Long criteriaGroupId) {

        return service.existsById(criteriaGroupId);

    }
    
}
