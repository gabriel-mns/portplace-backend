package com.pucpr.portplace.authentication.features.ahp.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.Evaluation;
import com.pucpr.portplace.authentication.features.project.mappers.ProjectMapper;

@Mapper(componentModel = "spring", uses = {ProjectMapper.class, CriterionMapper.class, AHPMapper.class})
public interface EvaluationMapper {
    
    @Mapping(target = "ahp.id", source = "ahpId")
    @Mapping(target = "project.id", source = "projectId")
    @Mapping(target = "criterion.id", source = "criterionId")
    @Mapping(target = "id", ignore = true)
    Evaluation toEntity(EvaluationCreateDTO dto);

    @Mapping(target = "ahpId", source = "ahp.id")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "criterionId", source = "criterion.id")
    EvaluationReadDTO toReadDTO(Evaluation evaluation);

    @Mapping(target = "ahpId", source = "ahp.id")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "criterionId", source = "criterion.id")
    List<EvaluationReadDTO> toReadDTO(List<Evaluation> evaluation);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ahp", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "criterion", ignore = true)
    void updateFromDTO(EvaluationUpdateDTO dto, @MappingTarget Evaluation entity);

}
