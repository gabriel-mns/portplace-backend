package com.pucpr.portplace.features.ahp.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.ahp.enums.CriteriaGroupStatusEnum;
import com.pucpr.portplace.features.strategy.dtos.StrategyReadDTO;

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
    private CriteriaGroupStatusEnum status;

    //Relationships
    private StrategyReadDTO strategy;
    private List<CriterionReadDTO> criteria;
    private List<CriteriaComparisonReadDTO> criteriaComparisons;
    private int relatedObjectivesCount;
    private int relatedEvaluationGroupsCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private String createdBy;
    private boolean disabled;

}
