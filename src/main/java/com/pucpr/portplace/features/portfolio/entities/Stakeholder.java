package com.pucpr.portplace.features.portfolio.entities;

import java.util.List;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.portfolio.enums.PortfolioScaleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stakeholders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Stakeholder extends AuditableEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private PortfolioScaleEnum powerLevel;
    private String powerLevelJustification;
    private PortfolioScaleEnum interestLevel;
    private String interestLevelJustification;
    private String expectations;
    private String obligationsWithStakeholder;
    private String positivePoints;
    private String negativePoints;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
    @ManyToMany
    private List<EventParticipant> participantIn;

}
