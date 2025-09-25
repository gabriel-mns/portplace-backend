package com.pucpr.portplace.features.resource.dtos.position;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PositionUpdateDTO {

    @NotBlank
    private String name;
    @ValidEnum(enumClass = ResourceStatusEnum.class)
    private String status;
    
}
