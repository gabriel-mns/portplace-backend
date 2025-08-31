package com.pucpr.portplace.features.strategy.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.strategy.enums.StrategicObjectiveStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StrategicObjectiveReadDTO {
    
    private long id;
    private String name;
    private String description;
    private StrategicObjectiveStatusEnum status;
    private long strategyId;

    private int criteriaCount;
    private int activePortfolioCount;
    private int activeProjectsCount;

    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;

    //TODO: List evaluationGroups and objectives DTOs

}
