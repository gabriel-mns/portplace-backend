package com.pucpr.portplace.features.ahp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriterionCreateDTO {
    
    @NotBlank
    private String name;
    private String description;

}
