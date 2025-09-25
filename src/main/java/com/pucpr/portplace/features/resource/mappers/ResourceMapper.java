package com.pucpr.portplace.features.resource.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.resource.dtos.resource.ResourceCreateDTO;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceReadDTO;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceUpdateDTO;
import com.pucpr.portplace.features.resource.entities.Resource;

@Mapper(
    componentModel = "spring",
    uses = {PositionMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface ResourceMapper {
    
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "position.id", source = "positionId")
    Resource toEntity(ResourceCreateDTO dto);

    ResourceReadDTO toReadDTO(Resource entity);

    void updateFromDTO(ResourceUpdateDTO dto, @MappingTarget Resource entity);

}
