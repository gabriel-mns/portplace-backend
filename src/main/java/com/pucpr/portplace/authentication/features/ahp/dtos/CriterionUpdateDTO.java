package com.pucpr.portplace.authentication.features.ahp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriterionUpdateDTO {

    @NotBlank
    private String name;
    private String description;
    
}
