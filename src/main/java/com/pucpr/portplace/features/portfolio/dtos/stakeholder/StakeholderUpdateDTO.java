package com.pucpr.portplace.features.portfolio.dtos.stakeholder;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.portfolio.enums.PortfolioScaleEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StakeholderUpdateDTO {
    
    private String name;
    @ValidEnum(enumClass = PortfolioScaleEnum.class)
    private String powerLevel;
    private String powerLevelJustification;
    @ValidEnum(enumClass = PortfolioScaleEnum.class)
    private String interestLevel;
    private String interestLevelJustification;
    private String expectations;
    private String obligationsWithStakeholder;
    private String positivePoints;
    private String negativePoints;
    
}
