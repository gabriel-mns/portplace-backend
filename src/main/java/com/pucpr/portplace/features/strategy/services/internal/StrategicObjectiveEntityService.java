package com.pucpr.portplace.features.strategy.services.internal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.entities.StrategicObjective;
import com.pucpr.portplace.features.strategy.repositories.StrategicObjectiveRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategicObjectiveEntityService {
    
    private StrategicObjectiveRepository repository;

    public boolean existsById(long id){

        return repository.existsById(id);

    }

    public StrategicObjective findByID(long id){

        return repository.findById(id).get();

    }

    public List<StrategicObjective> findAllById(List<Long> strategicObjectives) {
        
        return repository.findAllById(strategicObjectives);

    }

}
