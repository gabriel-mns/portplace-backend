package com.pucpr.portplace.authentication.features.ahp.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupListReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;

@Mapper(componentModel = "spring", uses = {CriterionMapper.class, CriteriaComparisonMapper.class})
public interface CriteriaGroupMapper {
    
    @Mapping(source = "criteriaComparisons", target = "criteriaComparisons")
    @Mapping(source = "criteria", target = "criteriaList")
    @Mapping(source = "lastModifiedBy.id", target = "lastModifiedBy")
    public CriteriaGroupReadDTO toCriteriaGroupReadDTO(CriteriaGroup criteriaGroup);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criteriaComparisons", ignore = true)
    @Mapping(target = "criteria", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "strategy", ignore = true)
    public CriteriaGroup toCriteriaGroupEntity(CriteriaGroupCreateDTO criteriaGroupCreateDto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criteriaComparisons", ignore = true)
    @Mapping(target = "criteria", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "strategy", ignore = true)
    public void updateFromDTO(CriteriaGroupUpdateDTO criteriaGroupUpdateDto, @MappingTarget CriteriaGroup criteriaGroup);

    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "criteriaCount", source = "criteriaGroup", qualifiedByName = "mapCriteriaCount")
    @Mapping(target = "criteriaComparisonCount", source = "criteriaGroup", qualifiedByName = "mapComparisonCount")
    CriteriaGroupListReadDTO toCriteriaGroupListReadDTO(CriteriaGroup criteriaGroup);

    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "criteriaCount", source = "criteriaGroup", qualifiedByName = "mapCriteriaCount")
    @Mapping(target = "criteriaComparisonCount", source = "criteriaGroup", qualifiedByName = "mapComparisonCount")
    List<CriteriaGroupListReadDTO> toCriteriaGroupListReadDTO(List<CriteriaGroup> criteriaGroup);

    @Named("mapCriteriaCount")
    default int mapCriteriaCount(CriteriaGroup group) {
        return group.getCriteria() != null ? group.getCriteria().size() : 0;
    }
    @Named("mapComparisonCount")
    default int mapComparisonCount(CriteriaGroup group) {
        return group.getCriteriaComparisons() != null ? group.getCriteriaComparisons().size() : 0;
    }
    
}
