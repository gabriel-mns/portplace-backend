package com.pucpr.portplace.features.strategy.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioReadDTO;
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

    // Relationships
    private Long strategyId;
    private EvaluationGroupReadDTO evaluationGroup;
    private PortfolioReadDTO portfolio;
    private List<ScenarioRankingReadDTO> scenarioRankings;
    private int includedProjectsCount;
    
    // Auditing Fields
    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;

}
