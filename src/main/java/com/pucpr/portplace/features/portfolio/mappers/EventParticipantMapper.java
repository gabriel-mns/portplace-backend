package com.pucpr.portplace.features.portfolio.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.EventParticipant;

@Mapper(
    componentModel = "spring",
    uses = {StakeholderMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface EventParticipantMapper {
    
    EventParticipantReadDTO toReadDTO(EventParticipant entity);
    
    @Mapping(target = "stakeholder.id", source = "dto.stakeholderId")
    @Mapping(target = "event.id", source = "dto.eventId")
    EventParticipant toEntity(EventParticipantCreateDTO dto);

    void updateFromDTO(EventParticipantUpdateDTO dto, @MappingTarget EventParticipant entity);


}
