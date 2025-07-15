package com.pucpr.portplace.authentication.features.ahp.mappers;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;

@Mapper(componentModel = "spring", uses = { EvaluationMapper.class })
public interface AHPMapper {

    @Mapping(target = "criteriaGroup.id", source = "criteriaGroupId")
    @Mapping(target = "strategy.id", source = "strategyId")
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    AHP toEntity(AHPCreateDTO ahpCreateDTO);

    @Mapping(target = "criteriaGroupId", source = "criteriaGroup.id")
    @Mapping(target = "evaluations", source = "evaluations")
    AHPReadDTO toReadDTO(AHP ahp);

    @Mapping(target = "criteriaGroupId", source = "criteriaGroup.id")
    @Mapping(target = "evaluations", source = "evaluations")
    List<AHPReadDTO> toReadDTO(List<AHP> ahp);

    @Mapping(target = "criteriaGroup.id", source = "criteriaGroupId")
    @Mapping(target = "strategy.id", source = "strategyId")
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateFromDTO(AHPUpdateDTO ahpUpdateDTO, @MappingTarget AHP ahp);

    @AfterMapping
    default void handleNullDeptList(AHP source, @MappingTarget AHPReadDTO target) {
        if (source.getEvaluations() == null) {
            target.setEvaluations(new ArrayList<>());
        }
    }

}
