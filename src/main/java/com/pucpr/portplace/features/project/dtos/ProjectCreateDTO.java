package com.pucpr.portplace.features.project.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.core.validation.constraints.dateRange.ValidDateRange;
// import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ValidDateRange(endField = "endDate", startField = "startDate")
public class ProjectCreateDTO {

    @NotBlank
    private String name;
    private String description;
    // @ValidEnum(enumClass = ProjectStatusEnum.class)
    // private String status;
    private double payback;
    private double roi;
    
    // EVMS Fields
    private double earnedValue;
    private double plannedValue;
    private double actualCost;
    private double budgetAtCompletion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;

}
