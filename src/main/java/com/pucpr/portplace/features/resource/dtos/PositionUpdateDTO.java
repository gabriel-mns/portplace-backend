package com.pucpr.portplace.features.resource.dtos;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.resource.enums.PositionStatusEnum;

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
    @ValidEnum(enumClass = PositionStatusEnum.class)
    private String status;
    
}
