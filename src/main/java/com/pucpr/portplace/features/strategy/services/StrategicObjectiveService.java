package com.pucpr.portplace.features.strategy.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.StrategicObjective;
import com.pucpr.portplace.features.strategy.enums.StrategicObjectiveStatusEnum;
import com.pucpr.portplace.features.strategy.mappers.StrategicObjectiveMapper;
import com.pucpr.portplace.features.strategy.repositories.StrategicObjectiveRepository;
import com.pucpr.portplace.features.strategy.services.validations.StrategicObjectiveValidationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StrategicObjectiveService {
    
    private StrategicObjectiveValidationService validationService;
    private StrategicObjectiveRepository repository;
    private StrategicObjectiveMapper mapper;

    //CREATE
    public StrategicObjectiveReadDTO createStrategicObjective(
        @Valid StrategicObjectiveCreateDTO dto,
        long strategyId
    ){

        validationService.validateBeforeCreation(strategyId);

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

        validationService.validateBeforeUpdate(strategicObjectiveId);

        StrategicObjective objective = repository.findById(strategicObjectiveId).get();

        mapper.updateFromDTO(dto, objective);

        objective = repository.save(objective);

        return mapper.toReadDTO(objective);

    }


    //DELETE
    public void disableStrategicObjective(
        long strategicObjectiveId
    ){

        validationService.validateBeforeDisable(strategicObjectiveId);

        StrategicObjective objective = repository.findById(strategicObjectiveId).get();

        objective.setDisabled(true);
        objective.setStatus(StrategicObjectiveStatusEnum.CANCELLED);

        objective = repository.save(objective);

    }

    public void deleteStrategicObjective(
        long strategicObjectiveId
    ){

        repository.deleteById(strategicObjectiveId);

    }

    //READ

    public Page<StrategicObjectiveReadDTO> getStrategicObjectives(
        long strategyId,
        List<StrategicObjectiveStatusEnum> status,
        boolean includeDisabled,
        String objectiveName,
        Pageable pageable
    ){

        validationService.validateBeforeGetAll(strategyId);

        Page<StrategicObjective> objectives = repository.findByFilters(
            strategyId,
            status,
            includeDisabled,
            objectiveName,
            pageable
        );

        return objectives.map(mapper::toReadDTO);

    }

    public StrategicObjectiveReadDTO getStrategicObjective(
        long strategicObjectiveId
    ){

        validationService. validateBeforeGet(strategicObjectiveId);
        
        StrategicObjective objective = repository.findById(strategicObjectiveId).get();

        return mapper.toReadDTO(objective);

    }


}
