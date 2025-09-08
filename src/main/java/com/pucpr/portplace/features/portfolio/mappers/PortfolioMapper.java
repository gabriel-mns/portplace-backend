package com.pucpr.portplace.features.portfolio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.portfolio.dtos.PortfolioCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioListReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.project.mappers.ProjectMapper;
import com.pucpr.portplace.features.strategy.mappers.StrategyMapper;
import com.pucpr.portplace.features.user.mappers.UserMapper;

@Mapper(
    componentModel = "spring",
    uses = {ProjectMapper.class, UserMapper.class, PortfolioCategoryMapper.class, StrategyMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
    )
public interface PortfolioMapper {

    @Mapping(target = "status", constant = "EMPTY")
    @Mapping(target = "projects", ignore = true)
    Portfolio toEntity(PortfolioCreateDTO dto);
    
    PortfolioReadDTO toReadDTO(Portfolio portfolio);

    PortfolioListReadDTO toListReadDTO(Portfolio portfolio);

    void updateFromDTO(PortfolioUpdateDTO dto, @MappingTarget Portfolio entity);

}
