package com.pucpr.portplace.features.portfolio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.PortfolioCategory;

@Mapper(
    componentModel = "spring",
    uses = {PortfolioMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
public interface PortfolioCategoryMapper {

    @Mapping(target = "portfolioId", source = "portfolio.id")
    PortfolioCategoryReadDTO toReadDTO(PortfolioCategory entity);
    
    @Mapping(target= "portfolio.id", source = "portfolioId")
    PortfolioCategory toEntity(PortfolioCategoryCreateDTO dto);

    void updateFromDTO(PortfolioCategoryUpdateDTO dto, @MappingTarget PortfolioCategory entity);

}
