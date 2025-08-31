package com.pucpr.portplace.features.ahp.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.BiSpecification;
import com.pucpr.portplace.features.strategy.entities.StrategicObjective;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StrategicObjectiveFromSameStrategySpecification implements BiSpecification<StrategicObjective, Long>{ 

    @Override
    public boolean isSatisfiedBy(StrategicObjective strategicObjective, Long strategyId) {
        
        return strategicObjective.getStrategy().getId() == strategyId;

    }
    
}
