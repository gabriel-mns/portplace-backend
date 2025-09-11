package com.pucpr.portplace.features.portfolio.entities;

import java.util.ArrayList;
import java.util.List;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portfolio_categories")
public class PortfolioCategory extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
    @OneToMany(mappedBy = "portfolioCategory")
    private List<ScenarioRanking> scenarioRankings = new ArrayList<>();
    @OneToMany(mappedBy = "portfolioCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public boolean getCanBeDeleted() {
        return this.projects.isEmpty() && this.scenarioRankings.isEmpty();
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

}
