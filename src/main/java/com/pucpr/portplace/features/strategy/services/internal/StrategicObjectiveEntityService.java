package com.pucpr.portplace.features.strategy.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.repositories.StrategicObjectiveRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategicObjectiveEntityService {
    
    private StrategicObjectiveRepository repository;

    public boolean existsById(long id){

        return repository.existsById(id);

    }

}
