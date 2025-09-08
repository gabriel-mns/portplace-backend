package com.pucpr.portplace.features.strategy.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.pucpr.portplace.features.portfolio.entities.PortfolioCategory;
import com.pucpr.portplace.features.portfolio.mappers.PortfolioCategoryMapper;
import com.pucpr.portplace.features.project.mappers.ProjectMapper;
import com.pucpr.portplace.features.strategy.dtos.ScenarioRankingReadDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioRankingUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;

@Mapper(
    componentModel = "spring" ,   
    uses = {ProjectMapper.class, PortfolioCategoryMapper.class},
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ScenarioRankingMapper {
    
    ScenarioRankingReadDTO toReadDTO(ScenarioRanking scenarioRanking);

    @Mapping(target = "portfolioCategory", ignore = true)
    void updateFromDTO(ScenarioRankingUpdateDTO dto, @MappingTarget ScenarioRanking scenarioRanking);

    @AfterMapping
    default void updatePortfolioCategory(ScenarioRankingUpdateDTO dto, @MappingTarget ScenarioRanking target) {

        boolean dtoHasCategory = dto.getPortfolioCategoryId() != null;
        
        // Ignore if dto has no category
        if(!dtoHasCategory ) return;

        PortfolioCategory category = new PortfolioCategory();
        category.setId(dto.getPortfolioCategoryId());
        target.setPortfolioCategory(category);

    }
}
