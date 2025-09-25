package com.pucpr.portplace.features.resource.dtos.resource;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceUpdateDTO {
    
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Min(0)
    @Max(24)
    private Integer dailyHours;
    private Long positionId;
    @ValidEnum(enumClass = ResourceStatusEnum.class)
    private String status;

}
