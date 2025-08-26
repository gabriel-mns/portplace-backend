package com.pucpr.portplace.features.ahp.entities;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.strategy.entities.Scenario;
import com.pucpr.portplace.features.strategy.entities.Strategy;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evaluation_groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class EvaluationGroup extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    // Relationships
    @ManyToOne
    private Strategy strategy;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "criteria_group_id")
    private CriteriaGroup criteriaGroup;
    @OneToMany(mappedBy = "evaluationGroup")
    private List<Evaluation> evaluations;
    @OneToMany(mappedBy = "evaluationGroup")
    private List<Scenario> scenarios; 

}
