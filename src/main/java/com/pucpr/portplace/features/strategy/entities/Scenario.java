package com.pucpr.portplace.features.strategy.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.strategy.enums.ScenarioStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scenarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Scenario extends AuditableEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private float budget;
    private ScenarioStatusEnum status;

    //Relationships
    @OneToMany(mappedBy = "scenario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<ScenarioRanking> scenarioRankings = new ArrayList<>();
    @ManyToOne
    private Strategy strategy;
    @ManyToOne
    private EvaluationGroup evaluationGroup;
    // @ManyToOne
    // private Portfolio portfolio;

    public void addScenarioRanking(ScenarioRanking ranking) {
        this.scenarioRankings.add(ranking);
        ranking.setScenario(this);
    }

    public void addScenariosRankings(List<ScenarioRanking> rankings) {
        for (ScenarioRanking ranking : rankings) {
            addScenarioRanking(ranking);
        }
    }

    public void removeScenarioRanking(ScenarioRanking ranking) {
        this.scenarioRankings.remove(ranking);
        ranking.setScenario(null);
    }
    
}
