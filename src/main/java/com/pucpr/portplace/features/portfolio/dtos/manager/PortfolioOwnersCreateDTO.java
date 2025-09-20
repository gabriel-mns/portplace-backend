package com.pucpr.portplace.features.portfolio.dtos.manager;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PortfolioOwnersCreateDTO {
    
    private List<Long> ownersIds;

}
