package com.pucpr.portplace.features.resource.dtos.request;

import java.time.LocalDate;

import com.pucpr.portplace.core.validation.constraints.dateRange.ValidDateRange;
import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.resource.enums.PriorityEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ValidDateRange(startField = "startDate", endField = "endDate")
public class AllocationRequestCreateDTO {
    
    private LocalDate startDate;
    private LocalDate endDate;
    private int dailyHours;
    @ValidEnum(enumClass = PriorityEnum.class)
    private String priority;

    //Relationships
    @NotNull
    private Long positionId;
    @NotNull
    private Long projectId;

}
