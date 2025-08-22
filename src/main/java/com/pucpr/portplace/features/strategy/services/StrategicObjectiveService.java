package com.pucpr.portplace.features.strategy.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.StrategicObjective;
import com.pucpr.portplace.features.strategy.mappers.StrategicObjectiveMapper;
import com.pucpr.portplace.features.strategy.repositories.StrategicObjectiveRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategicObjectiveService {
    
    private StrategicObjectiveRepository repository;
    private StrategicObjectiveMapper mapper;

    //CREATE
    public StrategicObjectiveReadDTO createStrategicObjective(
        @Valid StrategicObjectiveCreateDTO dto,
        long strategyId
    ){

        dto.setStrategyId(strategyId);

        StrategicObjective newObjective = mapper.toEntity(dto);

        newObjective = repository.save(newObjective);

        return mapper.toReadDTO(newObjective);

    }

    //UPDATE
    public StrategicObjectiveReadDTO updateStrategicObjective(
        long strategicObjectiveId,
        StrategicObjectiveUpdateDTO dto
    ){

        //TODO: check if exists

        StrategicObjective objective = repository.findById(strategicObjectiveId).get();

        mapper.updateFromDTO(dto, objective);

        objective = repository.save(objective);

        return mapper.toReadDTO(objective);

    }


    //DELETE
    public void disableStrategicObjective(
        long strategicObjectiveId
    ){

        //TODO: check if exists

        StrategicObjective objective = repository.findById(strategicObjectiveId).get();

        objective.setDisabled(true);

        objective = repository.save(objective);

    }

    public void deleteStrategicObjective(
        long strategicObjectiveId
    ){

        //TODO: check if exists

        repository.deleteById(strategicObjectiveId);

    }

    //READ

    public Page<StrategicObjectiveReadDTO> getStrategicObjectives(
        long strategyId,
        String objectiveName,
        boolean includeDisabled,
        Pageable pageable
    ){

        //TODO: check if exists

        Page<StrategicObjective> objectives;

        if(includeDisabled){
            objectives = repository.findByStrategyIdAndNameContainingIgnoreCase(strategyId, objectiveName, pageable);
        }else{
            objectives = repository.findByDisabledFalseAndStrategyIdAndNameContainingIgnoreCase(strategyId, objectiveName, pageable);
        }

        return objectives.map(mapper::toReadDTO);

    }

    public StrategicObjectiveReadDTO getStrategicObjective(
        long strategicObjectiveId
    ){

        //TODO: check if exists

        StrategicObjective objective = repository.findById(strategicObjectiveId).get();

        return mapper.toReadDTO(objective);

    }


}
