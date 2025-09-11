package com.pucpr.portplace.features.strategy.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryReadDTO;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.strategy.enums.ScenarioRankingStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScenarioRankingReadDTO {
    
    private Long id;
    private int currentPosition;
    private int calculatedPosition;
    private int totalScore;
    private ScenarioRankingStatusEnum status;

    private ProjectReadDTO project;
    private PortfolioCategoryReadDTO portfolioCategory;

    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;

}
