package com.pucpr.portplace.features.strategy.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.repositories.ScenarioRankingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScenarioRankingEntityService {
    
    private ScenarioRankingRepository repository;

    public boolean existsById(long id){

        return repository.existsById(id);

    }

}
