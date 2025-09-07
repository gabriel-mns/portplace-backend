package com.pucpr.portplace.features.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvmEntryUpdateDTO {

    private Double plannedValue;
    private Double actualCost;
    private Double percentComplete;
    private Integer month;
    private Integer year;

    // Relationships
    private Long projectId;

}
