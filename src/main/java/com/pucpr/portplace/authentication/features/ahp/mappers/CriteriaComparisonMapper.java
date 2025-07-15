package com.pucpr.portplace.authentication.features.ahp.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.authentication.features.ahp.mappers.helpers.CriteriaComparisonMapHelper;

@Mapper(componentModel = "spring", uses = {CriterionMapper.class, CriteriaGroupMapper.class, CriteriaComparisonMapHelper.class})
public interface CriteriaComparisonMapper {
    
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "comparedCriterion.id", source = "comparedCriterionId")
    @Mapping(target = "criteriaGroup.id", source = "criteriaGroupId")
    @Mapping(target = "referenceCriterion.id", source = "referenceCriterionId")
    CriteriaComparison toEntity(CriteriaComparisonCreateDTO criteriaComparisonCreateDto);

    @Mapping(target = "comparedCriterionId", source = "comparedCriterion.id")
    @Mapping(target = "criteriaGroupId", source = "criteriaGroup.id")
    @Mapping(target = "referenceCriterionId", source = "referenceCriterion.id")
    CriteriaComparisonReadDTO toReadDTO(CriteriaComparison criteriaComparison);

    @Mapping(target = "comparedCriterionId", source = "comparedCriterion.id")
    @Mapping(target = "criteriaGroupId", source = "criteriaGroup.id")
    @Mapping(target = "referenceCriterionId", source = "referenceCriterion.id")
    List<CriteriaComparisonReadDTO> toReadDTO(List<CriteriaComparison> criteriaComparisonList);

    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "comparedCriterion", ignore = true)
    @Mapping(target = "referenceCriterion", ignore = true)
    @Mapping(target = "criteriaGroup", ignore = true)
    void updateFromDTO(CriteriaComparisonUpdateDTO dto, @MappingTarget CriteriaComparison entity);
}
