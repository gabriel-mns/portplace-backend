package com.pucpr.portplace.features.project.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryReadDTO;
import com.pucpr.portplace.features.project.enums.ProjectStatusEnum;

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
    private String name;
    private String description;
    private ProjectStatusEnum status;
    private double payback;
    private double roi;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    
    // EVMS Fields
    private double plannedValue;
    private double earnedValue;
    private double actualCost;
    private double budgetAtCompletion;
    private double percentComplete;
    private double costPerformanceIndex;
    private double schedulePerformanceIndex;
    private double estimateAtCompletion;
    private double estimateToComplete;
    
    // Relationships
    // private List<EvmEntryReadDTO> evmEntries;
    private PortfolioCategoryReadDTO portfolioCategory;


    // Auditable fields
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    // private UserGetResponseDTO createdBy;
    // private UserGetResponseDTO lastModifiedBy;
    private boolean disabled;

}
