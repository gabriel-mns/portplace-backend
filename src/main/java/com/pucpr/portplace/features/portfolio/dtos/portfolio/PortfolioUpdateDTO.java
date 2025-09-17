package com.pucpr.portplace.features.portfolio.dtos.portfolio;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioUpdateDTO {

    @NotBlank
    private String name;
    private String description;

}
