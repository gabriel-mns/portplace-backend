package com.pucpr.portplace.features.portfolio.dtos.stakeholder;

import com.pucpr.portplace.features.portfolio.enums.PortfolioScaleEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StakeholderReadDTO {
    
    private long id;
    private String name;
    private PortfolioScaleEnum powerLevel;
    private String powerLevelJustification;
    private PortfolioScaleEnum interestLevel;
    private String interestLevelJustification;
    private String expectations;
    private String obligationsWithStakeholder;
    private String positivePoints;
    private String negativePoints;
    private long portfolioId;

    //Auditing fields
    private String createdAt;
    private String lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;
    private boolean disabled;

}
