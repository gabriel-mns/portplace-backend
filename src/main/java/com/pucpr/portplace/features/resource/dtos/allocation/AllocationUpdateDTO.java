package com.pucpr.portplace.features.resource.dtos.allocation;

import java.time.LocalDate;

import com.pucpr.portplace.core.validation.constraints.dateRange.ValidDateRange;

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
@ValidDateRange(startField = "startDate", endField = "endDate")
public class AllocationUpdateDTO {
    
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Min(0)
    @Max(24)
    private int dailyHours;

    @NotNull
    private Long resourceId;

}
