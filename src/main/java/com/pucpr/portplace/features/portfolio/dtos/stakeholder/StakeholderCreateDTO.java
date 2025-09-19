package com.pucpr.portplace.features.portfolio.dtos.stakeholder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StakeholderCreateDTO {
    
    private String name;
    private Long portfolioId;

}
