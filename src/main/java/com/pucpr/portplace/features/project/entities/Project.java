package com.pucpr.portplace.features.project.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.entities.PortfolioCategory;
import com.pucpr.portplace.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;
import com.pucpr.portplace.features.user.entities.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Project extends AuditableEntity{
    
    //#region General fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectStatusEnum status;
    private double payback;
    
    //#region EVMS fields
    private double earnedValue;
    private double plannedValue;
    private double actualCost;
    private double budgetAtCompletion; //used to calculate ROI

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    //#region Relationships
    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private User projectManager;
    @OneToMany(mappedBy = "project")
    private List<ScenarioRanking> scenarioRankings;
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_category_id")
    private PortfolioCategory portfolioCategory;

    /*
     *
     * WARNING: This was removed because it`s not needed right now, but it can be useful in the future
     * 
     */
    // @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // private List<EvmEntry> evmEntries;

    //#region  Methods
    public Project(String name, String description, ProjectStatusEnum status, double earnedValue, double plannedValue,
            double actualCost, double budget, double payback, LocalDate startDate, LocalDate endDate, User projectManager) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.earnedValue = earnedValue;
        this.plannedValue = plannedValue;
        this.actualCost = actualCost;
        this.payback = payback;
        this.startDate = startDate;
        this.endDate = endDate;
        // this.projectManager = projectManager;
    }

    public double getCostPerformanceIndex() {
        // updateCalculatedValues();
        if (this.actualCost == 0) return 1;
        return this.earnedValue / this.actualCost;
    }

    public double getSchedulePerformanceIndex() {
        // updateCalculatedValues();
        if (this.plannedValue == 0) return 1;
        return this.earnedValue / this.plannedValue;
    }

    public double getEstimateAtCompletion() {
        if (this.getCostPerformanceIndex() == 0) return this.budgetAtCompletion;
        return this.budgetAtCompletion / this.getCostPerformanceIndex();
    }

    public double getEstimateToComplete() {
        return this.getEstimateAtCompletion() - this.actualCost;
    }

    public double getPercentComplete() {
        // updateCalculatedValues();
        if (this.budgetAtCompletion == 0) return 0;
        return (this.earnedValue / this.budgetAtCompletion) * 100;
    }

    public double getRoi() {
        if (this.budgetAtCompletion == 0) return 0;
        return ((this.earnedValue - this.budgetAtCompletion) / this.budgetAtCompletion) * 100;
    }




    /*
     * 
     * WARNING: These methods were removed because they are not needed right now, but they can be useful in the future
     * 
     */

    // private void updateEarnedValue() {
        
    //     List<EvmEntry> entries = this.evmEntries;

    //     if (entries == null || entries.isEmpty()) {
    //         this.totalEarnedValue = 0;
    //         return;
    //     }

    //     this.totalEarnedValue = entries.stream()
    //             .filter(ev -> !ev.isDisabled())
    //             .mapToDouble(EvmEntry::getPlannedValue)
    //             .sum();

    // }

    // private void updateActualCost() {

    //     if (this.evmEntries == null || this.evmEntries.isEmpty()) {
    //         this.totalActualCost = 0;
    //         return;
    //     }

    //     this.totalActualCost = this.evmEntries.stream()
    //             .filter(ev -> !ev.isDisabled())
    //             .mapToDouble(EvmEntry::getActualCost)
    //             .sum();

    // }

    // private void updatePlannedValue() {

    //     if (this.evmEntries == null || this.evmEntries.isEmpty()) {
    //         this.totalPlannedValue = 0;
    //         this.currentPlannedValue = 0;
    //         return;
    //     }

    //     int currentMonth = LocalDate.now().getMonthValue();
    //     int currentYear = LocalDate.now().getYear();

    //     this.totalPlannedValue = this.evmEntries.stream()
    //             .filter(ev -> !ev.isDisabled())
    //             .mapToDouble(EvmEntry::getPlannedValue)
    //             .sum();

    //     this.currentPlannedValue = this.evmEntries.stream()
    //         .filter(ev -> !ev.isDisabled())
    //         .filter(ev -> 
    //             (ev.getYear() < currentYear) ||
    //             (ev.getYear() == currentYear && ev.getMonth() <= currentMonth)
    //         )
    //         .mapToDouble(EvmEntry::getPlannedValue)
    //         .sum();

    // }

    // public void updateCalculatedValues() {
    //     updateActualCost();
    //     updatePlannedValue();
    //     updateEarnedValue();
    // }

}
