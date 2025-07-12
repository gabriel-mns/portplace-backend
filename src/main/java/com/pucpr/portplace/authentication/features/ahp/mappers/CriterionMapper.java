package com.pucpr.portplace.authentication.features.ahp.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;

@Mapper(componentModel = "spring", uses = {CriteriaGroupMapper.class})
public interface CriterionMapper {
    
    @Mapping(target = "criteriaGroupId", source = "criteriaGroup.id") 
    CriterionReadDTO toCriterionReadDTO(Criterion criterion);
    
    List<CriterionReadDTO> toCriterionReadDTOList(List<Criterion> criteria);

    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "weight", ignore = true)
    @Mapping(target = "criteriaGroup", ignore = true)
    Criterion toCriterionEntity(CriterionCreateDTO criterionCreateDto);

    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "weight", ignore = true)
    @Mapping(target = "criteriaGroup", ignore = true)
    void updateFromDTO(CriterionUpdateDTO criterionUpdateDto, @MappingTarget Criterion criterion);

}
