package com.pucpr.portplace.features.strategy.dtos;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.strategy.enums.StrategicObjectiveStatusEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StrategicObjectiveUpdateDTO {
    
    @NotBlank
    private String name;
    private String description;
    @ValidEnum(enumClass = StrategicObjectiveStatusEnum.class)
    private String status;

    //TODO: add strategy objectives and other entities related

}
