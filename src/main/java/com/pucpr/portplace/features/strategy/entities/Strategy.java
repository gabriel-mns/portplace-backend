package com.pucpr.portplace.features.strategy.entities;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.strategy.enums.StrategyStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "strategies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Strategy extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private StrategyStatusEnum status;

    @Formula("(SELECT COUNT(*) FROM strategic_objectives so WHERE so.strategy_id = id AND so.status = 1 AND so.disabled = false)")
    private int activeObjectivesCount;

    @OneToMany(mappedBy = "strategy")
    private List<StrategicObjective> strategicObjectives;
    @OneToMany(mappedBy = "strategy")
    private List<CriteriaGroup> criteriaGroups;
    @OneToMany(mappedBy = "strategy")
    private List<EvaluationGroup> evaluationGroups;
    @OneToMany(mappedBy = "strategy")
    private List<Scenario> scenarios; 

}
