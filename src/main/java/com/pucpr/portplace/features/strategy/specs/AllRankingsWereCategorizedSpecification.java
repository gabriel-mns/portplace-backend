package com.pucpr.portplace.features.strategy.specs;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllRankingsWereCategorizedSpecification implements Specification<List<ScenarioRanking>> {
    
    @Override
    public boolean isSatisfiedBy(List<ScenarioRanking> rankings) {
        // Check if all ScenarioRanking objects in the list are categorized
        return rankings.stream().allMatch(ScenarioRanking::isCategorized);
    }
    
}
