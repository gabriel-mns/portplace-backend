package com.pucpr.portplace.features.strategy.mappers;

import java.util.ArrayList;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.pucpr.portplace.features.ahp.mappers.EvaluationGroupMapper;
import com.pucpr.portplace.features.strategy.dtos.StrategyCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.Strategy;

@Mapper(
    componentModel = "spring" ,   
    uses = {StrategicObjectiveMapper.class, EvaluationGroupMapper.class},
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StrategyMapper {
    
    StrategyReadDTO toReadDTO(Strategy strategy);

    @Mapping(target = "status", constant = "ACTIVE")
    Strategy toEntity(StrategyCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(StrategyUpdateDTO dto, @MappingTarget Strategy entity);

    @AfterMapping
    default void handleNullDeptList(Strategy source, @MappingTarget StrategyReadDTO target) {
        if(source.getStrategicObjectives() == null){
            target.setStrategicObjectives(new ArrayList<>());
        }
        if(source.getEvaluationGroups() == null){
            target.setEvaluationGroups(new ArrayList<>());
        }
    }

}
