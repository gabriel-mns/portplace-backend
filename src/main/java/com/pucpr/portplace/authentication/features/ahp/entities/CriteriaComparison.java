package com.pucpr.portplace.authentication.features.ahp.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.authentication.core.entities.AuditableEntity;
import com.pucpr.portplace.authentication.features.ahp.enums.ImportanceScale;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "criteria_comparisons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CriteriaComparison extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compared_criterion_id")
    private Criterion comparedCriterion;

    @ManyToOne
    @JoinColumn(name = "reference_criterion_id")
    private Criterion referenceCriterion;

    @Enumerated(EnumType.STRING)
    private ImportanceScale importanceScale;

    @ManyToOne
    @JoinColumn(name = "criteria_group_id")
    private CriteriaGroup criteriaGroup;

}
