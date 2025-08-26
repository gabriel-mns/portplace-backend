package com.pucpr.portplace.features.strategy.dtos;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.strategy.enums.ScenarioRankingStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScenarioRankingUpdateDTO {
    
    // private int customPosition;
    @ValidEnum(enumClass = ScenarioRankingStatusEnum.class)
    private String status;

}