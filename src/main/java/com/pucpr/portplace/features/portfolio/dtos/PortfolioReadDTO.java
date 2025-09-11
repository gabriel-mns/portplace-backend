package com.pucpr.portplace.features.portfolio.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.portfolio.enums.PortfolioHealthEnum;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyReadDTO;
import com.pucpr.portplace.features.user.dtos.UserGetResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioReadDTO {

    private Long id;
    private String name;
    private String description;
    private double budget;
    private PortfolioStatusEnum status;

    // Relationships
    private List<ProjectReadDTO> projects;
    private StrategyReadDTO strategy;
    private List<UserGetResponseDTO> owners;
    
    // Calculated Fields
    private PortfolioHealthEnum scheduleHealth;
    private PortfolioHealthEnum budgetHealth;

    // Auditing Fields
    private String createdBy;
    private String lastModifiedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;

}
