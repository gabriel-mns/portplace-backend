package com.pucpr.portplace.features.strategy.specs;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;
import com.pucpr.portplace.features.strategy.enums.ScenarioRankingStatusEnum;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AllRankingsWereCategorizedSpecification implements Specification<List<ScenarioRanking>> {
    
    @Override
    public boolean isSatisfiedBy(List<ScenarioRanking> rankings) {
        // Check if all ScenarioRanking objects in the list are categorized
        return rankings.stream()
        .filter(ranking -> ranking.getStatus() != ScenarioRankingStatusEnum.MANUALLY_EXCLUDED && ranking.getStatus() != ScenarioRankingStatusEnum.EXCLUDED)
        .allMatch(ScenarioRanking::isCategorized);
    }
    
}
