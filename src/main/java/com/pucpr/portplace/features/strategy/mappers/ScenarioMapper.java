package com.pucpr.portplace.features.strategy.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.pucpr.portplace.features.ahp.mappers.EvaluationGroupMapper;
import com.pucpr.portplace.features.portfolio.mappers.PortfolioMapper;
import com.pucpr.portplace.features.strategy.dtos.ScenarioAuthorizationPreviewDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioReadDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.Scenario;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;
import com.pucpr.portplace.features.strategy.enums.ScenarioRankingStatusEnum;
@Mapper(
    componentModel = "spring" ,   
    uses = {ScenarioRankingMapper.class, EvaluationGroupMapper.class, PortfolioMapper.class},
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ScenarioMapper {
 
    @Mapping(target = "strategyId", source = "strategy.id")
    ScenarioReadDTO toReadDTO(Scenario scenario);

    @Mapping(target = "status", constant = "WAITING_AUTHORIZATION")
    @Mapping(target = "evaluationGroup.id", source = "dto.evaluationGroupId")
    @Mapping(target = "strategy.id", source = "dto.strategyId")
    @Mapping(target = "portfolio.id", source = "dto.portfolioId")
    Scenario toEntity(ScenarioCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(ScenarioUpdateDTO dto, @MappingTarget Scenario entity);

    @Mapping(target = "strategyName", source = "scenario.strategy.name")
    @Mapping(target = "includedProjects", ignore = true) // setado depois com portfolio
    @Mapping(target = "removedProjects", ignore = true) // setado depois com portfolio
    @Mapping(target = "availableBudget", source = "scenario.budget")
    @Mapping(target = "totalProjectCost", expression = "java(calcTotalProjectCost(scenario.getScenarioRankings()))")
    @Mapping(target = "budgetBalance", expression = "java(scenario.getBudget() - calcTotalProjectCost(scenario.getScenarioRankings()))")
    ScenarioAuthorizationPreviewDTO toAuthorizationPreviewDTO(Scenario scenario);

    @AfterMapping
    default void handleNullDeptList(Scenario source, @MappingTarget ScenarioReadDTO target) {
        if(source.getScenarioRankings() == null) {
            target.setScenarioRankings(new ArrayList<>());
        }
    }

    default double calcTotalProjectCost(List<ScenarioRanking> rankings) {
        if (rankings == null) return 0;
        return rankings.stream()
                .filter(r -> r.getStatus() == ScenarioRankingStatusEnum.INCLUDED 
                          || r.getStatus() == ScenarioRankingStatusEnum.MANUALLY_INCLUDED)
                .mapToDouble(r -> r.getProject().getPlannedValue())
                .sum();
    }

}
