package com.pucpr.portplace.features.portfolio.entities;

import java.util.List;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.strategy.entities.Strategy;
import com.pucpr.portplace.features.user.entities.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "portfolios")
public class Portfolio extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double budget;
    @Enumerated(EnumType.STRING)
    private PortfolioStatusEnum status;

    // Relationships
    @OneToMany(mappedBy = "portfolio")
    private List<Project> projects;
    @ManyToMany
    private List<User> owners;
    @ManyToMany
    private List<PortfolioCategory> categories;
    @ManyToOne
    private Strategy strategy;
    // private List<Risk> risks;
    // private CommunicationPlan communicationPlan;

    // Calculated Fields
    // private PortfolioHealthEnum scheduleHealth;
    // private PortfolioHealthEnum budgetHealth;

}
