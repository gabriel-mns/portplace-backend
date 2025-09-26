package com.pucpr.portplace.features.resource.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.project.mappers.ProjectMapper;
import com.pucpr.portplace.features.resource.dtos.request.AllocationRequestCreateDTO;
import com.pucpr.portplace.features.resource.dtos.request.AllocationRequestReadDTO;
import com.pucpr.portplace.features.resource.dtos.request.AllocationRequestUpdateDTO;
import com.pucpr.portplace.features.resource.entities.AllocationRequest;
import com.pucpr.portplace.features.resource.entities.Position;

@Mapper(
    componentModel = "spring",
    uses = {Position.class, AllocationMapper.class, ProjectMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface AllocationRequestMapper {
    
    @Mapping(target = "status", constant = "IN_ANALYSIS")
    @Mapping(source = "positionId", target = "position.id")
    @Mapping(source = "projectId", target = "project.id")
    AllocationRequest toEntity(AllocationRequestCreateDTO dto);

    AllocationRequestReadDTO toReadDTO(AllocationRequest entity);

    void updateFromDTO(AllocationRequestUpdateDTO dto, @MappingTarget AllocationRequest entity);

}
