package com.pucpr.portplace.features.strategy.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.strategy.enums.ScenarioRankingStatusEnum;
import com.pucpr.portplace.features.strategy.enums.ScenarioStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private double budget;
    @Enumerated(EnumType.STRING)
    private ScenarioStatusEnum status;
    private LocalDateTime authorizationDate;

    // Relationships
    @OneToMany(mappedBy = "scenario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<ScenarioRanking> scenarioRankings = new ArrayList<>();
    @ManyToOne
    private Strategy strategy;
    @ManyToOne
    private EvaluationGroup evaluationGroup;
    @ManyToOne
    private Portfolio portfolio;

    // Calculated Fields
    @Formula("""
        (
            SELECT COUNT(DISTINCT sr.id)
            FROM scenario_rankings sr
            WHERE sr.scenario_id = id
            AND sr.disabled = false
            AND (sr.status = 1 OR sr.status = 2)
        )
    """)
    private int includedProjectsCount;


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

    public List<Project> getIncludedProjectsWithCategory() {
        
        return this.scenarioRankings.stream()
            .filter(ranking -> ranking.getStatus() == ScenarioRankingStatusEnum.INCLUDED ||
                               ranking.getStatus() == ScenarioRankingStatusEnum.MANUALLY_INCLUDED
                               )
            .map(ranking -> {
                Project project = ranking.getProject();
                project.setPortfolioCategory(ranking.getPortfolioCategory());
                return project;
            })
            .toList();
    }

    public List<Project> getNewIncludedProjects() {
        
        List<Project> includedProjects = getIncludedProjectsWithCategory();

        return includedProjects.stream()
            .filter(project -> !this.portfolio.getProjects().contains(project))
            .toList();
    }

    public List<Project> getRemovedProjects() {

        List<Project> includedProjects = getIncludedProjectsWithCategory();

        return this.portfolio.getProjects().stream()
            .filter(project -> !includedProjects.contains(project))
            .toList();
    }
    
}
