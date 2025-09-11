package com.pucpr.portplace.features.portfolio.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PortfolioCancelationPatchDTO {
    
    @NotBlank
    private String cancellationReason;

}
