package com.pucpr.portplace.features.portfolio.dtos.risk.occurence;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.portfolio.enums.RiskOccurrenceStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RiskOccurrenceReadDTO {

    private Long id;
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateOfOccurrence;
    private RiskOccurrenceStatusEnum status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime solvedAt;
    private int daysToSolve;
    private boolean followedPreventionPlan;
    private String preventionActions;
    private boolean followedContingencyPlan;
    private String contingencyActions;

    //Relationships
    // private RiskReadDTO risk;
    private Long riskId;

    // Auditing Fields
    private boolean disabled;
    private String createdBy;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime lastModifiedAt;

}
