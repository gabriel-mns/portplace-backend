package com.pucpr.portplace.authentication.features.ahp.entities;

import com.pucpr.portplace.authentication.features.ahp.enums.ImportanceScale;

import jakarta.persistence.Entity;
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
public class CriteriaComparison {
    
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
    @JoinColumn(name = "ahp_id")
    private AHP ahp;

    @Builder.Default
    private boolean disabled = false;

}
