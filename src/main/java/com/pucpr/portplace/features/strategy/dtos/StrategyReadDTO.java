package com.pucpr.portplace.features.strategy.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupReadDTO;
import com.pucpr.portplace.features.strategy.enums.StrategyStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StrategyReadDTO {
    
    private long id;
    private String name;
    private String description;
    private StrategyStatusEnum status;
    private List<StrategicObjectiveReadDTO> strategicObjectives;
    private List<EvaluationGroupReadDTO> evaluationGroups;

    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;

    //TODO: List evaluationGroups and objectives DTOs

}
