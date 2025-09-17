package com.pucpr.portplace.features.portfolio.dtos.portfolio;

import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioListReadDTO {

    private Long id;
    private String name;
    private double budget;
    private PortfolioStatusEnum status;

    // Calculated Fields
    private int inProgressProjectsCount;
    private int completedProjectsCount;
    private int cancelledProjectsCount;


}
