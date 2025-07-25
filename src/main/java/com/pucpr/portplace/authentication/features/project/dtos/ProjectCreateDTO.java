package com.pucpr.portplace.authentication.features.project.dtos;

import java.time.LocalDate;

import com.pucpr.portplace.authentication.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.authentication.core.validation.constraints.dateRange.ValidDateRange;
import com.pucpr.portplace.authentication.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.authentication.features.user.constraintValidators.UserExists;

import jakarta.validation.constraints.Min;
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
    @ValidEnum(enumClass = ProjectStatusEnum.class)
    private String status;
    private double earnedValue;
    private double plannedValue;
    private double actualCost;
    @Min(0)
    private double budget;
    private double payback;
    private LocalDate startDate;
    private LocalDate endDate;
    private long projectManager;

}
