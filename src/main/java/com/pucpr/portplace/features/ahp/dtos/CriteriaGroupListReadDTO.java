package com.pucpr.portplace.features.ahp.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.ahp.enums.CriteriaGroupStatusEnum;

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
public class CriteriaGroupListReadDTO {

    private long id;
    private String name;
    private String description;
    private CriteriaGroupStatusEnum status;
    
    // Relationships
    private int relatedObjectivesCount;
    private int relatedEvaluationGroupsCount;
    // private int criteriaCount;
    // private int criteriaComparisonCount;

    // Audit fields
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private String createdBy;
    private boolean disabled;

}
