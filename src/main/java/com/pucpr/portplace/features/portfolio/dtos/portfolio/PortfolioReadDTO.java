package com.pucpr.portplace.features.portfolio.dtos.portfolio;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.portfolio.dtos.portfolioCategory.PortfolioCategoryReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.RiskReadDTO;
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
    private String cancellationReason;
    private String communicationStorageDescription;

    // Relationships
    private List<ProjectReadDTO> projects;
    private List<UserGetResponseDTO> owners;
    private List<PortfolioCategoryReadDTO> categories;
    private List<RiskReadDTO> risks;
    private StrategyReadDTO strategy;
    private String activeScenarioName;
    private boolean canBeDeleted;
    
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
