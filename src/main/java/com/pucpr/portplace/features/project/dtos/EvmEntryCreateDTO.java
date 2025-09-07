package com.pucpr.portplace.features.project.dtos;

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
    private Double percentComplete;
    @NotNull
    private Integer month;
    @NotNull
    private Integer year;

    // Relationships
    private Long projectId;

}
