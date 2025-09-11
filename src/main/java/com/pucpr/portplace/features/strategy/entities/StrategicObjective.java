package com.pucpr.portplace.features.strategy.entities;

import java.util.List;

import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.strategy.enums.StrategicObjectiveStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @Enumerated(EnumType.STRING)
    private StrategicObjectiveStatusEnum status;

    //Calculated Fields
    @Formula("""
        (SELECT COUNT(c.criterion_id)
         FROM criterion_strategic_objective c
         WHERE c.strategic_objective_id = id)
        """)
    private int criteriaCount;
    
    @Formula("""
        (SELECT COUNT(DISTINCT p.id)
        FROM portfolios p
            JOIN scenarios s ON s.id = p.scenario_id
            JOIN evaluation_groups eg ON eg.id = s.evaluation_group_id
            JOIN criteria_groups cg ON cg.id = eg.criteria_group_id
            JOIN criteria c ON c.criteria_group_id = cg.id
            JOIN criterion_strategic_objective cso ON cso.criterion_id = c.id
            JOIN strategic_objectives so ON so.id = cso.strategic_objective_id
        WHERE so.id = id
            AND p.status = 'IN_PROGRESS'
            AND p.disabled = false
            AND s.disabled = false
            AND eg.disabled = false
            AND cg.disabled = false
            AND c.disabled = false)
    """)
    private int activePortfolioCount;


    @Formula("""
        (SELECT COUNT(DISTINCT p.id)
            FROM projects p
                JOIN scenario_rankings sr ON sr.project_id = p.id
                JOIN scenarios s ON s.id = sr.scenario_id
                JOIN evaluation_groups eg ON eg.id = s.evaluation_group_id
                JOIN criteria_groups cg ON cg.id = eg.criteria_group_id
                JOIN criteria c ON c.criteria_group_id = cg.id
                JOIN criterion_strategic_objective cso ON cso.criterion_id = c.id
                JOIN strategic_objectives so ON so.id = cso.strategic_objective_id
            WHERE so.id = id
                AND p.status = 'IN_PROGRESS'
                AND p.disabled = false
                AND s.disabled = false
                AND eg.disabled = false
                AND cg.disabled = false
                AND c.disabled = false)
        """)
    private int activeProjectsCount;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;
    @ManyToMany(mappedBy = "strategicObjectives")
    // @JoinTable(
    //     name = "strategic_objective_criterion",
    //     joinColumns = @JoinColumn(name = "strategic_objective_id"),
    //     inverseJoinColumns = @JoinColumn(name = "criterion_id")
    // )
    private List<Criterion> criteria;
    // private List<Portfolio> portfolios; TODO: Implement portfolio

}
