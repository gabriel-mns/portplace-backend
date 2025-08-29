package com.pucpr.portplace.features.ahp.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupListReadDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupReadDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;

@Mapper(
    componentModel = "spring", 
    uses = {CriterionMapper.class, CriteriaComparisonMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CriteriaGroupMapper {
    
    @Mapping(source = "criteriaComparisons", target = "criteriaComparisons")
    @Mapping(source = "criteria", target = "criteriaList")
    public CriteriaGroupReadDTO toCriteriaGroupReadDTO(CriteriaGroup criteriaGroup);

    public CriteriaGroup toCriteriaGroupEntity(CriteriaGroupCreateDTO criteriaGroupCreateDto);

    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateFromDTO(CriteriaGroupUpdateDTO criteriaGroupUpdateDto, @MappingTarget CriteriaGroup criteriaGroup);

    @Mapping(target = "criteriaCount", source = "criteriaGroup", qualifiedByName = "mapCriteriaCount")
    @Mapping(target = "criteriaComparisonCount", source = "criteriaGroup", qualifiedByName = "mapComparisonCount")
    CriteriaGroupListReadDTO toCriteriaGroupListReadDTO(CriteriaGroup criteriaGroup);

    @Mapping(target = "criteriaCount", source = "criteriaGroup", qualifiedByName = "mapCriteriaCount")
    @Mapping(target = "criteriaComparisonCount", source = "criteriaGroup", qualifiedByName = "mapComparisonCount")
    List<CriteriaGroupListReadDTO> toCriteriaGroupListReadDTO(List<CriteriaGroup> criteriaGroup);

    @Named("mapCriteriaCount")
    default int mapCriteriaCount(CriteriaGroup group) {
        if (group.getCriteria() == null) return 0;
        return (int) group.getCriteria().stream()
            .filter(c -> !c.isDisabled())
            .count();
    }

    @Named("mapComparisonCount")
    default int mapComparisonCount(CriteriaGroup group) {
        if (group.getCriteriaComparisons() == null) return 0;
        return (int) group.getCriteriaComparisons().stream()
            .filter(c -> !c.isDisabled())
            .count();
    }
    
    @AfterMapping
    default void handleNullDeptList(CriteriaGroup source, @MappingTarget CriteriaGroupReadDTO target) {
        if (source.getCriteria() == null) {
            target.setCriteriaList(new ArrayList<>());
        }
        if(source.getCriteriaComparisons() == null) {
            target.setCriteriaComparisons(new ArrayList<>());
        }
    }
    
    @AfterMapping
    default void handleNullDeptList(CriteriaGroup source, @MappingTarget CriteriaGroupListReadDTO target) {
        if (source.getCriteria() == null) {
            target.setCriteriaCount(0);
        }
        if(source.getCriteriaComparisons() == null) {
            target.setCriteriaComparisonCount(0);
        }
    }

}
