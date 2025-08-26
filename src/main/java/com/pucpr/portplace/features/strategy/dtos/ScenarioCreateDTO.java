package com.pucpr.portplace.features.strategy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioCreateDTO {
    
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Float budget;
    @NotNull
    private Long evaluationGroupId;
    private Long strategyId;
    // private Long portfolioId;0

}
