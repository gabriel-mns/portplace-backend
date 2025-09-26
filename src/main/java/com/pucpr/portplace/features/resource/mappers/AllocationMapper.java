package com.pucpr.portplace.features.resource.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.features.resource.dtos.allocation.AllocationCreateDTO;
import com.pucpr.portplace.features.resource.dtos.allocation.AllocationReadDTO;
import com.pucpr.portplace.features.resource.dtos.allocation.AllocationUpdateDTO;
import com.pucpr.portplace.features.resource.entities.Allocation;

@Mapper(
    componentModel = "spring",
    uses = {ResourceMapper.class},
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface AllocationMapper {
    
    Allocation toEntity(AllocationCreateDTO dto);
    
    @Mapping(source = "allocationRequest.id", target = "allocationRequestId")
    AllocationReadDTO toReadDTO(Allocation entity);

    @Mapping(target = "resource.id", ignore = true)
    void updateFromDTO(AllocationUpdateDTO dto, @MappingTarget Allocation entity);

}
