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
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring", 
    uses = {ProjectMapper.class, CriterionMapper.class, AHPMapper.class},
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EvaluationMapper {
    
    @Mapping(target = "project.id", source = "projectId")
    @Mapping(target = "criterion.id", source = "criterionId")
    Evaluation toEntity(EvaluationCreateDTO dto);

    @Mapping(target = "ahpId", source = "ahp.id")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "criterionId", source = "criterion.id")
    EvaluationReadDTO toReadDTO(Evaluation evaluation);

    @Mapping(target = "ahpId", source = "ahp.id")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "criterionId", source = "criterion.id")
    List<EvaluationReadDTO> toReadDTO(List<Evaluation> evaluation);
    
    void updateFromDTO(EvaluationUpdateDTO dto, @MappingTarget Evaluation entity);

}
