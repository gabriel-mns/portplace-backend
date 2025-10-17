package com.pucpr.portplace.features.portfolio.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.Formula;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.portfolio.enums.RiskOccurrenceStatusEnum;

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

@Entity
@Table(name = "risk_occurences")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RiskOccurrence extends AuditableEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime dateOfOccurrence;
    @Enumerated(EnumType.STRING)
    private RiskOccurrenceStatusEnum status;
    private LocalDateTime solvedAt;
    private boolean followedPreventionPlan;
    private String preventionActions;
    private boolean followedContingencyPlan;
    private String contingencyActions;

    // Calculated Fields
    @Formula("""
        (COALESCE(solved_at::date, CURRENT_DATE) - date_of_occurrence::date)
    """)
    private Integer daysToSolve;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "risk_id", nullable = false)
    private Risk risk;

    public void setSolvedAt(LocalDateTime solvedAt) {

        this.solvedAt = solvedAt;

        if (solvedAt != null) {
            this.status = RiskOccurrenceStatusEnum.SOLVED;
        } else {
            this.status = RiskOccurrenceStatusEnum.NOT_SOLVED;
        }

    }

}
