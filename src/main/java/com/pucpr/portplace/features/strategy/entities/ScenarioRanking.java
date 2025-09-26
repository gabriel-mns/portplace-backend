package com.pucpr.portplace.features.strategy.entities;

// import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.portfolio.entities.PortfolioCategory;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.strategy.enums.ScenarioRankingStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scenario_rankings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ScenarioRanking extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int currentPosition;
    private int calculatedPosition;
    private double totalScore;
    @Enumerated(EnumType.ORDINAL)
    private ScenarioRankingStatusEnum status;
    

    //Relationships
    @ManyToOne(cascade = CascadeType.ALL)
    private PortfolioCategory portfolioCategory;
    @ManyToOne
    private Scenario scenario;
    @ManyToOne
    private Project project;

    public boolean isCategorized() {
        return portfolioCategory != null;
    }

}
