package com.pucpr.portplace.authentication.features.project.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.authentication.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.authentication.features.user.dtos.UserGetResponseDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProjectReadDTO {

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private boolean disabled;
    private UserGetResponseDTO projectManager;

}
