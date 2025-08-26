package com.pucpr.portplace.features.strategy.dtos;

import java.util.List;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.strategy.enums.ScenarioStatusEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioUpdateDTO {

    @NotBlank
    private String name;
    private String description;
    private float budget;
    @ValidEnum(enumClass = ScenarioStatusEnum.class)
    private String status;

}
