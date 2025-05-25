package com.pucpr.portplace.authentication.features.ahp.dtos;

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
public class CriterionCreateDTO {
    
    @NotBlank
    @NotNull
    private String name;
    private String description;
    private long criteriaGroupId;

}
