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
import com.pucpr.portplace.features.strategy.dtos.ScenarioCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioReadDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.Scenario;

@Mapper(
    componentModel = "spring" ,   
    uses = {ScenarioRankingMapper.class, EvaluationGroupMapper.class},
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ScenarioMapper {
 
    @Mapping(target = "strategyId", source = "strategy.id")
    // @Mapping(target = "evaluationGroupId", source = "evaluationGroup.id")
    ScenarioReadDTO toReadDTO(Scenario scenario);

    @Mapping(target = "status", constant = "WAITING_AUTHORIZATION")
    @Mapping(target = "evaluationGroup.id", source = "dto.evaluationGroupId")
    @Mapping(target = "strategy.id", source = "dto.strategyId")
    Scenario toEntity(ScenarioCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(ScenarioUpdateDTO dto, @MappingTarget Scenario entity);
    
    @AfterMapping
    default void handleNullDeptList(Scenario source, @MappingTarget ScenarioReadDTO target) {
        if(source.getScenarioRankings() == null) {
            target.setScenarioRankings(new ArrayList<>());
        }
    }

}
