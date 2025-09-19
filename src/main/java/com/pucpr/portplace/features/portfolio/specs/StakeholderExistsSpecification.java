package com.pucpr.portplace.features.portfolio.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.portfolio.repositories.StakeholderRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StakeholderExistsSpecification implements Specification<Long> {

    private StakeholderRepository repository;

    @Override
    public boolean isSatisfiedBy(Long id) {
        return repository.existsById(id);
    }
    
}
