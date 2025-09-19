package com.pucpr.portplace.features.portfolio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.portfolio.dtos.event.EventCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.Event;

@Mapper(
    componentModel = "spring",
    uses = {StakeholderMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface EventMapper {
    
    @Mapping(target = "portfolio.id", source = "dto.portfolioId")
    Event toEntity(EventCreateDTO dto);
    
    void updateFromDto(EventUpdateDTO dto, @MappingTarget Event entity);
    
    @Mapping(target = "portfolioId", source = "portfolio.id")
    EventReadDTO toReadDTO(Event event);
}
