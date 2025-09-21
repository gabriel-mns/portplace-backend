package com.pucpr.portplace.features.portfolio.dtos.risk;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurrenceReadDTO;
import com.pucpr.portplace.features.portfolio.enums.RiskScaleEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RiskReadDTO {
    
    private Long id;
    private String name;
    private String description;
    private RiskScaleEnum probability;
    private String probabilityDescription;
    private RiskScaleEnum impact;
    private String impactDescription;
    private String preventionPlan;
    private String contingencyPlan;

    // Calculated Fields
    private Integer severity;

    // Relationships
    private Long portfolioId;
    private List<RiskOccurrenceReadDTO> occurrences;

    // Auditing Fields
    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;

}
