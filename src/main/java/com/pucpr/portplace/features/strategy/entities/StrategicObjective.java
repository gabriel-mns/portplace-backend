package com.pucpr.portplace.features.strategy.entities;

import java.util.List;

import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.strategy.enums.StrategicObjectiveStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "strategic_objectives")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class StrategicObjective extends AuditableEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private StrategicObjectiveStatusEnum status;

    //Calculated Fields
    @Formula("""
        (SELECT COUNT(c.criterion_id)
         FROM criterion_strategic_objective c
         WHERE c.strategic_objective_id = id)
        """)
    private int criteriaCount;
    //TODO: calculate this field when portfolio is created
    // private int activePortfolioCount;
    @Formula("""
        (SELECT COUNT(DISTINCT p.project_id)
         FROM strategic_objective_project p
         INNER JOIN projects pr ON pr.id = p.project_id
         WHERE p.strategic_objective_id = id
           AND pr.status = 2)
        """)
    private int activeProjectsCount;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;
    @ManyToMany
    @JoinTable(
        name = "strategic_objective_project",
        joinColumns = @JoinColumn(name = "strategic_objective_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects;
    @ManyToMany(mappedBy = "strategicObjectives")
    // @JoinTable(
    //     name = "strategic_objective_criterion",
    //     joinColumns = @JoinColumn(name = "strategic_objective_id"),
    //     inverseJoinColumns = @JoinColumn(name = "criterion_id")
    // )
    private List<Criterion> criteria;
    // private List<Portfolio> portfolios; TODO: Implement portfolio

}
