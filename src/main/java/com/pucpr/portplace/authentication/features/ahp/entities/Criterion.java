package com.pucpr.portplace.authentication.features.ahp.entities;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.authentication.core.entities.AuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "criteria")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Criterion extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Transient
    private double weight;
    
    // Relationships
    @ManyToOne
    @JoinColumn(name = "criteria_group_id")
    private CriteriaGroup criteriaGroup;
    @OneToMany(mappedBy = "comparedCriterion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CriteriaComparison> comparedInComparisons;
    @OneToMany(mappedBy = "referenceCriterion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CriteriaComparison> referenceInComparisons;

}
