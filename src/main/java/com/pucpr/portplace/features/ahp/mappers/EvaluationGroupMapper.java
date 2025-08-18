package com.pucpr.portplace.features.ahp.mappers;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupReadDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupUpdateDTO;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;

@Mapper(
    componentModel = "spring", 
    uses = { EvaluationMapper.class },
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EvaluationGroupMapper {

    @Mapping(target = "criteriaGroup.id", source = "criteriaGroupId")
    @Mapping(target = "strategy.id", source = "strategyId")
    EvaluationGroup toEntity(EvaluationGroupCreateDTO evaluationGroupCreateDTO);

    @Mapping(target = "criteriaGroupId", source = "criteriaGroup.id")
    @Mapping(target = "evaluations", source = "evaluations")
    EvaluationGroupReadDTO toReadDTO(EvaluationGroup evaluationGroup);

    @Mapping(target = "criteriaGroupId", source = "criteriaGroup.id")
    @Mapping(target = "evaluations", source = "evaluations")
    List<EvaluationGroupReadDTO> toReadDTO(List<EvaluationGroup> evaluationGroup);

    // @Mapping(target = "criteriaGroup.id", source = "criteriaGroupId")
    @Mapping(target = "strategy.id", source = "strategyId")
    void updateFromDTO(EvaluationGroupUpdateDTO evaluationGroupUpdateDTO, @MappingTarget EvaluationGroup evaluationGroup);

    @AfterMapping
    default void handleNullDeptList(EvaluationGroup source, @MappingTarget EvaluationGroupReadDTO target) {
        if (source.getEvaluations() == null) {
            target.setEvaluations(new ArrayList<>());
        }
    }

}
