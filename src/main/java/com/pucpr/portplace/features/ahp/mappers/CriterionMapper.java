package com.pucpr.portplace.features.ahp.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.pucpr.portplace.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.ahp.mappers.helpers.CriterionMapHelper;

@Mapper(
    componentModel = "spring", 
    uses = {CriteriaGroupMapper.class, CriteriaComparisonMapper.class, CriterionMapHelper.class},
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CriterionMapper {
    
    @Mapping(target = "criteriaGroupId", source = "criteriaGroup.id") 
    CriterionReadDTO toReadDTO(Criterion criterion);
    
    List<CriterionReadDTO> toReadDTO(List<Criterion> criteria);

    Criterion toEntity(CriterionCreateDTO criterionCreateDto);

    @Mapping(target = "strategicObjectives", ignore = true)
    void updateFromDTO(CriterionUpdateDTO criterionUpdateDto, @MappingTarget Criterion criterion);

}
