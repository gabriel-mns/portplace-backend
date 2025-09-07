package com.pucpr.portplace.features.strategy.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.entities.Scenario;
import com.pucpr.portplace.features.strategy.repositories.ScenarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScenarioEntityService {
    
    private ScenarioRepository repository;

    public void save(Scenario scenario){

        repository.save(scenario);

    }

    public boolean existsById(long id){

        return repository.existsById(id);

    }

    public Scenario getScenarioById(long id){

        return repository.findById(id).get();

    }

}
