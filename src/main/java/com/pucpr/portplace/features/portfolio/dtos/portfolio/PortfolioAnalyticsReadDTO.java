package com.pucpr.portplace.features.portfolio.dtos.portfolio;

import java.util.List;

import com.pucpr.portplace.features.portfolio.dtos.risk.RiskReadDTO;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveReadDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PortfolioAnalyticsReadDTO {

    private PortfolioReadDTO portfolio;
    private List<ProjectReadDTO> projects;
    private List<RiskReadDTO> risks;
    private List<StrategicObjectiveReadDTO> strategicObjectives;

}
