package com.pucpr.portplace.features.portfolio.entities;

import java.util.List;

import org.hibernate.annotations.Formula;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.portfolio.enums.PortfolioHealthEnum;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.strategy.entities.Strategy;
import com.pucpr.portplace.features.user.entities.User;

import jakarta.persistence.CascadeType;
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
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;
    @ManyToMany
    private List<User> owners;
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioCategory> categories;
    @ManyToOne
    private Strategy strategy;
    // private List<Risk> risks;
    // private CommunicationPlan communicationPlan;

    // Calculated Fields
    @Formula("""
        (
            SELECT COUNT(*) 
            FROM projects p 
            WHERE p.portfolio_id = id 
            AND p.status = 'IN_PROGRESS'
            AND p.disabled = false
            )""")
    private int inProgressProjectsCount;
    @Formula("""
        (
            SELECT COUNT(*) 
            FROM projects p 
            WHERE p.portfolio_id = id 
            AND p.status = 'COMPLETED'
            AND p.disabled = false
            )""")
    private int completedProjectsCount;
    @Formula("""
        (
            SELECT COUNT(*) 
            FROM projects p 
            WHERE p.portfolio_id = id 
            AND p.status = 'CANCELLED'
            AND p.disabled = false
            )""")
    private int cancelledProjectsCount;
    // private PortfolioHealthEnum scheduleHealth;
    // private PortfolioHealthEnum budgetHealth;

    public PortfolioHealthEnum getScheduleHealth() {

        int lateProjects = 0;

        for (Project project : projects) {

            double scheduleHealth = project.getSchedulePerformanceIndex();

            if (scheduleHealth < 1) {
                lateProjects++;
            }
            
        }

        if (lateProjects > 2) {
            return PortfolioHealthEnum.RED;
        } else if (lateProjects > 0) {
            return PortfolioHealthEnum.YELLOW;
        } else {
            return PortfolioHealthEnum.GREEN;
        }

    }

    public PortfolioHealthEnum getBudgetHealth() {

        int overBudgetProjects = 0;

        for (Project project : projects) {

            double costHealth = project.getCostPerformanceIndex();

            if (costHealth < 1) {
                overBudgetProjects++;
            }
            
        }

        if (overBudgetProjects > 2) {
            return PortfolioHealthEnum.RED;
        } else if (overBudgetProjects > 0) {
            return PortfolioHealthEnum.YELLOW;
        } else {
            return PortfolioHealthEnum.GREEN;
        }

    }

    public void addProject(Project project) {
        this.projects.add(project);
        project.setPortfolio(this);
    }

}
