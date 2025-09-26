package com.pucpr.portplace.features.portfolio.dtos.portfolioCategory;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PortfolioCategoryUpdateDTO {

    @NotBlank
    private String name;
    private String description;

}
