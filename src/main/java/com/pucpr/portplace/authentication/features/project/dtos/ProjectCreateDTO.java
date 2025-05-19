package com.pucpr.portplace.authentication.features.project.dtos;

import java.time.LocalDate;
import java.util.Date;

import com.pucpr.portplace.authentication.features.project.enums.ProjectStatusEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectCreateDTO {

    @NotBlank
    private String name;
    private String description;
    private ProjectStatusEnum status;
    private double earnedValue;
    private double plannedValue;
    private double actualCost;
    private double budget;
    private double payback;
    private LocalDate startDate;
    private LocalDate endDate;
    // private User projectManager;
    private long projectManager;

}
