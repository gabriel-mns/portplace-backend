package com.pucpr.portplace.authentication.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.authentication.core.validation.specs.Specification;
import com.pucpr.portplace.authentication.features.ahp.services.internal.CriteriaGroupEntityService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CriteriaGroupExistsSpecification implements Specification<Long> {

    CriteriaGroupEntityService service;

    @Override
    public boolean isSatisfiedBy(Long criteriaGroupId) {

        return service.existsById(criteriaGroupId);

    }
    
}
