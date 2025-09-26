package com.pucpr.portplace.features.portfolio.dtos.event;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventCreateDTO {
    
    @NotBlank
    private String name;
    private String description;
    private Long portfolioId;

}
