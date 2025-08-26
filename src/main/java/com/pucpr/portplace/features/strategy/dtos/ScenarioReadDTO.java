package com.pucpr.portplace.features.strategy.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.strategy.enums.ScenarioStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioReadDTO {
    
    private Long id;
    private String name;
    private String description;
    private float budget;
    private ScenarioStatusEnum status;

    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;

    private Long strategyId;
    private Long evaluationGroupId;

    private List<ScenarioRankingReadDTO> scenarioRankings;

}
