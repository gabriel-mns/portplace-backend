package com.pucpr.portplace.features.strategy.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StrategicObjectiveCreateDTO {
    
    @NotBlank
    private String name;
    private String description;
    private Long strategyId;

}
