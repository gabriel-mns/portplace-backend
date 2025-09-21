package com.pucpr.portplace.features.portfolio.entities;

import org.hibernate.annotations.Formula;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.portfolio.enums.RiskScaleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "risks")
public class Risk extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private RiskScaleEnum probability;
    private String probabilityDescription;
    @Enumerated(value = EnumType.STRING)
    private RiskScaleEnum impact;
    private String impactDescription;
    private String preventionPlan;
    private String contingencyPlan;

    // Calculated Fields
    @Formula(
        """
        (CASE
            WHEN probability = 'LOW' THEN 1
            WHEN probability = 'MEDIUM' THEN 2
            WHEN probability = 'HIGH' THEN 3
            WHEN probability = 'VERY_HIGH' THEN 4
        END) 
        * 
        (CASE
            WHEN impact = 'LOW' THEN 1
            WHEN impact = 'MEDIUM' THEN 2
            WHEN impact = 'HIGH' THEN 3
            WHEN impact = 'VERY_HIGH' THEN 4
        END
        )
    """
    )
    private Integer severity;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    // private List<Occurrences> occurrences;

}
