package com.pucpr.portplace.features.strategy.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.pucpr.portplace.features.project.mappers.ProjectMapper;
import com.pucpr.portplace.features.strategy.dtos.ScenarioRankingReadDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioRankingUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;

@Mapper(
    componentModel = "spring" ,   
    uses = {ProjectMapper.class},
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ScenarioRankingMapper {
    
    ScenarioRankingReadDTO toReadDTO(ScenarioRanking scenarioRanking);

    void updateFromDTO(ScenarioRankingUpdateDTO dto, @MappingTarget ScenarioRanking scenarioRanking);

}
