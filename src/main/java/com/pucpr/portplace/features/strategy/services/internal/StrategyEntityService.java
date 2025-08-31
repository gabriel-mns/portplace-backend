package com.pucpr.portplace.features.strategy.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.repositories.StrategyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategyEntityService {
    
    private StrategyRepository repository;

    public boolean existsById(long id){

        return repository.existsById(id);

    }

}
