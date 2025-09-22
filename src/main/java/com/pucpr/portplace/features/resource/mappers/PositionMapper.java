package com.pucpr.portplace.features.resource.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.portfolio.mappers.StakeholderMapper;
import com.pucpr.portplace.features.resource.dtos.PositionCreateDTO;
import com.pucpr.portplace.features.resource.dtos.PositionReadDTO;
import com.pucpr.portplace.features.resource.dtos.PositionUpdateDTO;
import com.pucpr.portplace.features.resource.entities.Position;

@Mapper(
    componentModel = "spring",
    uses = {StakeholderMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface PositionMapper {
    
    @Mapping(target = "status", constant = "ACTIVE")
    Position toEntity(PositionCreateDTO dto);

    PositionReadDTO toReadDTO(Position entity);

    void updateFromDTO(PositionUpdateDTO dto, @MappingTarget Position entity);

}
