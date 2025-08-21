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
public class StrategyUpdateDTO {
    
    @NotBlank
    private String name;
    private String description;

    //TODO: add strategy objectives and other entities related

}
