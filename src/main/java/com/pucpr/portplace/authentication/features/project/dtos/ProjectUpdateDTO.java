package com.pucpr.portplace.authentication.features.project.dtos;

import java.util.Date;

import com.pucpr.portplace.authentication.features.project.enums.ProjectStatusEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectUpdateDTO {

    @NotNull
    private long id;
    @NotBlank
    private String name;
    private String description;
    private ProjectStatusEnum status;
    private double earnedValue;
    private double plannedValue;
    private double actualCost;
    private double budget;
    private double payback;
    private Date startDate;
    private Date endDate;
    // private User projectManager;
    private long projectManager;

}
