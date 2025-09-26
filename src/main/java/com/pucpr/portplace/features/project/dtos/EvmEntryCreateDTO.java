package com.pucpr.portplace.features.project.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvmEntryCreateDTO {
    
    @NotNull
    private Double plannedValue;
    @NotNull
    private Double actualCost;
    @NotNull
    private Double earnedValue;
    // private Double percentComplete;
    @NotNull
    @Max(12)
    @Min(1)
    private Integer month;
    @NotNull
    @Min(0)
    private Integer year;

    // Relationships
    private Long projectId;

}
