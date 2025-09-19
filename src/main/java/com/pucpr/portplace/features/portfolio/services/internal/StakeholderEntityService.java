package com.pucpr.portplace.features.portfolio.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.entities.Stakeholder;
import com.pucpr.portplace.features.portfolio.repositories.StakeholderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StakeholderEntityService {
    
    private StakeholderRepository repository;

    public Stakeholder getStakeholderById(Long id) {
        return repository.findById(id).get();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

}
