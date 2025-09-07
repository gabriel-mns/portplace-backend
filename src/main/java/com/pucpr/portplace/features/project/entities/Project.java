package com.pucpr.portplace.features.project.entities;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;
import com.pucpr.portplace.features.strategy.entities.StrategicObjective;
import com.pucpr.portplace.features.user.entities.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectStatusEnum status;
    private double earnedValue;
    private double totalPlannedValue;
    private double totalActualCost;
    private double percentComplete;
    // private double plannedValue;
    // private double actualCost;
    // private double budget;
    private double payback;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private User projectManager;
    @ManyToMany(mappedBy = "projects")
    private List<StrategicObjective> strategicObjectives;
    @OneToMany(mappedBy = "project")
    private List<ScenarioRanking> scenarioRankings;
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EvmEntry> evmEntries;

    // TODO: Create attachments table and add a list of attachments to the project
    // private List<Attachment> attachments;

    public Project(String name, String description, ProjectStatusEnum status, double earnedValue, double plannedValue,
            double actualCost, double budget, double payback, LocalDate startDate, LocalDate endDate, User projectManager) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.earnedValue = earnedValue;
        this.totalPlannedValue = plannedValue;
        this.totalActualCost = actualCost;
        // this.plannedValue = plannedValue;
        // this.actualCost = actualCost;
        // this.budget = budget;
        this.payback = payback;
        this.startDate = startDate;
        this.endDate = endDate;
        // this.projectManager = projectManager;
    }

    private void updatePercentComplete() {

        // If there are no EVM entries, percent complete is 0
        if (this.evmEntries == null || this.evmEntries.isEmpty()) {
            this.percentComplete = 0;
            return;
        }

        List<EvmEntry> orderedEvmEntries = this.evmEntries.stream()
                .filter(ev -> !ev.isDisabled())
                .sorted(Comparator.comparing(EvmEntry::getYear)
                        .thenComparing(EvmEntry::getMonth)
                        .reversed())
                .collect(Collectors.toList());

        // If there ARE EVM entries
        if (!orderedEvmEntries.isEmpty()) {
            // Get the most recent entry (first in the ordered list)
            this.percentComplete = orderedEvmEntries.get(0).getPercentComplete();
        } else {
            this.percentComplete = 0;
        }

    }

    private void updateEarnedValue() {
        this.earnedValue = this.totalPlannedValue * (this.percentComplete / 100);
    }

    private void updateTotalActualCost() {

        if (this.evmEntries == null || this.evmEntries.isEmpty()) {
            this.totalActualCost = 0;
            return;
        }

        this.totalActualCost = this.evmEntries.stream()
                .filter(ev -> !ev.isDisabled())
                .mapToDouble(EvmEntry::getActualCost)
                .sum();

    }

    private void updateTotalPlannedValue() {

        if (this.evmEntries == null || this.evmEntries.isEmpty()) {
            this.totalPlannedValue = 0;
            return;
        }

        this.totalPlannedValue = this.evmEntries.stream()
                .filter(ev -> !ev.isDisabled())
                .mapToDouble(EvmEntry::getPlannedValue)
                .sum();

    }

    public void updateCalculatedValues() {
        updatePercentComplete();
        updateTotalActualCost();
        updateTotalPlannedValue();
        updateEarnedValue();
    }

}
