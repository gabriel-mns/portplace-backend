package com.pucpr.portplace.features.strategy.dtos;

import java.util.List;

import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScenarioAuthorizationPreviewDTO {
    
    private long id;
    private String name;
    private String strategyName;

    private List<ProjectReadDTO> includedProjects;
    private List<ProjectReadDTO> removedProjects;
    private double availableBudget;
    private double totalProjectCost;
    private double budgetBalance; // availableBudget - totalProjectCost

}
