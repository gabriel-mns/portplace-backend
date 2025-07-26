package com.pucpr.portplace.features.ahp.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CriteriaGroupReadDTO {

    private long id;
    private String name;
    private String description;
    //TODO: Create strategy objectives CRUD
    //List<StrategyObjective> strategyObjectives;
    private List<CriterionReadDTO> criteriaList;
    private List<CriteriaComparisonReadDTO> criteriaComparisons;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private boolean disabled;

}
