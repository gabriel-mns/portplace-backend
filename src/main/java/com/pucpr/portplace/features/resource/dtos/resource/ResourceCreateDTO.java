package com.pucpr.portplace.features.resource.dtos.resource;

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
public class ResourceCreateDTO {
    
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Min(0)
    @Max(24)
    private Integer dailyHours;
    @NotNull
    private Long positionId;

}
