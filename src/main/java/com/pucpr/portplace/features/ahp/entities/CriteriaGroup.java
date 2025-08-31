package com.pucpr.portplace.features.ahp.entities;

import java.util.List;

import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.ahp.enums.CriteriaGroupStatusEnum;
import com.pucpr.portplace.features.strategy.entities.Strategy;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "criteria_groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CriteriaGroup extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private CriteriaGroupStatusEnum status;
    
    // Relationships
    @ManyToOne
    private Strategy strategy;
    @OneToMany(mappedBy = "criteriaGroup")
    private List<Criterion> criteria;
    @OneToMany(mappedBy = "criteriaGroup")
    private List<CriteriaComparison> criteriaComparisons;

    // Calculated fields
    @Formula("""
        (
            SELECT COUNT(DISTINCT cso.strategic_objective_id)
            FROM criterion_strategic_objective cso
            JOIN criteria c ON c.id = cso.criterion_id
            WHERE c.criteria_group_id = id
        )
    """)
    private int relatedObjectivesCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CriteriaGroup)) return false;
        CriteriaGroup that = (CriteriaGroup) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode(); // Ou Objects.hash(id);
    }

}
