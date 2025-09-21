package com.pucpr.portplace.features.portfolio.dtos.risk;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.portfolio.enums.RiskScaleEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RiskUpdateDTO {

    private String name;
    private String description;
    @ValidEnum(enumClass = RiskScaleEnum.class)
    private String probability;
    private String probabilityDescription;
    @ValidEnum(enumClass = RiskScaleEnum.class)
    private String impact;
    private String impactDescription;
    private String preventionPlan;
    private String contingencyPlan;

    // Calculated Fields
    private Integer severity;

    // Relationships

    // Auditing Fields
    private boolean disabled;
    
    @JsonFormat(pattern = "dd/mm/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/mm/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;

}
