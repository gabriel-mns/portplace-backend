package com.pucpr.portplace.features.portfolio.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.entities.RiskOccurrence;
import com.pucpr.portplace.features.portfolio.repositories.RiskOccurrenceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RiskOccurrenceEntityService {
    
    private RiskOccurrenceRepository repository;

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public void save(RiskOccurrence entity) {
        repository.save(entity);
    }

}
