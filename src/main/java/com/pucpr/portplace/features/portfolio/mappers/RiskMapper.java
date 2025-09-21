package com.pucpr.portplace.features.portfolio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.portfolio.dtos.risk.RiskCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.RiskReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.RiskUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.Risk;

@Mapper(
    componentModel = "spring",
    uses = {StakeholderMapper.class, RiskOccurrenceMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface RiskMapper {
    
    @Mapping(target = "portfolio.id", source = "dto.portfolioId")
    @Mapping(target = "probability", constant = "LOW")
    @Mapping(target = "impact", constant = "LOW")
    Risk toEntity(RiskCreateDTO dto);
    
    void updateFromDTO(RiskUpdateDTO dto, @MappingTarget Risk entity);
    
    @Mapping(target = "portfolioId", source = "portfolio.id")
    RiskReadDTO toReadDTO(Risk event);

}
