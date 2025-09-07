package com.pucpr.portplace.features.strategy.services.internal;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;
import com.pucpr.portplace.features.strategy.repositories.ScenarioRankingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScenarioRankingEntityService {
    
    private ScenarioRankingRepository repository;

    
    public void recalculateCurrentPositions(List<ScenarioRanking> rankings) {
        
        int position = 1;

        orderRankingByStatusOrder(rankings);
        
        for (ScenarioRanking ranking : rankings) {
            ranking.setCurrentPosition(position++);
        }

    }

    public void orderRankingByStatusOrder(List<ScenarioRanking> rankings) {

        rankings.sort(Comparator.comparing(ScenarioRanking::getStatus));

        rankings.sort(Comparator.comparing(ScenarioRanking::getCalculatedPosition));

        rankings.sort(
            Comparator.comparing(ScenarioRanking::getStatus)
                    .thenComparing(ScenarioRanking::getCalculatedPosition)
        );

    }

    public boolean existsById(long id){

        return repository.existsById(id);

    }

}
