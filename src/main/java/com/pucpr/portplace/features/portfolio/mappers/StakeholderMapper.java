package com.pucpr.portplace.features.portfolio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.portfolio.dtos.stakeholder.StakeholderCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.stakeholder.StakeholderReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.stakeholder.StakeholderUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.Stakeholder;

@Mapper(
    componentModel = "spring",
    uses = {},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
    )
public interface StakeholderMapper {
    
    @Mapping(target = "portfolio.id", source = "portfolioId")
    @Mapping(target = "powerLevel", constant = "LOW")
    @Mapping(target = "interestLevel", constant = "LOW")
    Stakeholder toEntity(StakeholderCreateDTO stakeholderCreateDTO);
    
    @Mapping(target = "portfolioId", source = "portfolio.id")
    StakeholderReadDTO toReadDTO(Stakeholder stakeholder);

    void updateFromDTO(StakeholderUpdateDTO dto, @MappingTarget Stakeholder entity);

}
