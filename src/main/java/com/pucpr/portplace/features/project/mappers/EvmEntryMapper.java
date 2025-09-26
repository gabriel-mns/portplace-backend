package com.pucpr.portplace.features.project.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.pucpr.portplace.features.project.dtos.EvmEntryCreateDTO;
import com.pucpr.portplace.features.project.dtos.EvmEntryReadDTO;
import com.pucpr.portplace.features.project.dtos.EvmEntryUpdateDTO;
import com.pucpr.portplace.features.project.entities.EvmEntry;
import com.pucpr.portplace.features.user.mappers.UserMapper;

@Mapper(
    componentModel = "spring", 
    uses = {UserMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EvmEntryMapper {

    @Mapping(target = "project.id", source = "projectId")
    EvmEntry toEntity(EvmEntryCreateDTO dto);

    @Mapping(target = "project.id", source = "projectId")
    List<EvmEntryReadDTO> toReadDTOs(List<EvmEntry> entities);

    @Mapping(target = "projectId", source = "project.id")
    EvmEntryReadDTO toReadDTO(EvmEntry entity);

    void updateFromDTO(EvmEntryUpdateDTO dto, @MappingTarget EvmEntry entity);

}
