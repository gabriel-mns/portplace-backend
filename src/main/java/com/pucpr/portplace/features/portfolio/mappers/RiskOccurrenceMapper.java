package com.pucpr.portplace.features.portfolio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurenceCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurrenceReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurrenceUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.RiskOccurrence;

@Mapper(
    componentModel = "spring",
    uses = {},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface RiskOccurrenceMapper {
    
    @Mapping(target = "risk.id", source = "riskId")
    RiskOccurrence toEntity(RiskOccurenceCreateDTO dto);
    
    @Mapping(target = "riskId", source = "risk.id")
    RiskOccurrenceReadDTO toReadDTO(RiskOccurrence entity);

    void updateFromDTO(RiskOccurrenceUpdateDTO dto, @MappingTarget RiskOccurrence occurrence);

}
