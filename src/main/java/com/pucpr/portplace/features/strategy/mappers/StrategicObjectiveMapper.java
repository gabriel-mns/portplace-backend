package com.pucpr.portplace.features.strategy.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.StrategicObjective;

@Mapper(
    componentModel = "spring" ,   
    uses = {},
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StrategicObjectiveMapper {
    
    @Mapping(target = "strategyId", source = "strategy.id")
    StrategicObjectiveReadDTO toReadDTO(StrategicObjective strategicObjective);

    @Mapping(target = "status", constant = "IDLE")
    @Mapping(target = "strategy.id", source = "strategyId")
    StrategicObjective toEntity(StrategicObjectiveCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(StrategicObjectiveUpdateDTO dto, @MappingTarget StrategicObjective entity);

}
