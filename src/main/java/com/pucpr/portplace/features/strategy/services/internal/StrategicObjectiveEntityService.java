package com.pucpr.portplace.features.strategy.services.internal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveReadDTO;
import com.pucpr.portplace.features.strategy.entities.StrategicObjective;
import com.pucpr.portplace.features.strategy.mappers.StrategicObjectiveMapper;
import com.pucpr.portplace.features.strategy.repositories.StrategicObjectiveRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategicObjectiveEntityService {
    
    private StrategicObjectiveRepository repository;
    private StrategicObjectiveMapper mapper;

    public boolean existsById(long id){

        return repository.existsById(id);

    }

    public StrategicObjective findByID(long id){

        return repository.findById(id).get();

    }

    public List<StrategicObjective> findAllById(List<Long> strategicObjectives) {
        
        return repository.findAllById(strategicObjectives);

    }

    public List<StrategicObjectiveReadDTO> findObjectivesByProjectId(Long projectId) {

        List<StrategicObjective> objectives = repository.findObjectivesByProjectId(projectId);

        return objectives.stream().map(mapper::toReadDTO).toList();
    }

}
