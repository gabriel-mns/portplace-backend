package com.pucpr.portplace.authentication.features.project.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.authentication.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.authentication.features.user.entities.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projects")
@NoArgsConstructor
// @AllArgsConstructor
@Builder
@Getter
@Setter
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String name;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private ProjectStatusEnum status;
    private double earnedValue;
    private double plannedValue;
    private double actualCost;
    private double budget;
    private double payback;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private User projectManager;
    // TODO: Implement project evaluation
    // @OneToMany
    // private List<Evaluation> evaluations;
    // TODO: Create attachments table and add a list of attachments to the project
    // private List<Attachment> attachments;
    // TODO: Uncomment when Portfolio is implemented
    // private Portfolio portfolio;

    public Project(String name, String description, ProjectStatusEnum status, double earnedValue, double plannedValue,
            double actualCost, double budget, double payback, LocalDate startDate, LocalDate endDate, User projectManager) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.earnedValue = earnedValue;
        this.plannedValue = plannedValue;
        this.actualCost = actualCost;
        this.budget = budget;
        this.payback = payback;
        this.startDate = startDate;
        this.endDate = endDate;
        // this.projectManager = projectManager;
    }

    public Project(long id, String name, String description, ProjectStatusEnum status, double earnedValue, double plannedValue,
            double actualCost, double budget, double payback, LocalDate startDate, LocalDate endDate, User projectManager) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.earnedValue = earnedValue;
        this.plannedValue = plannedValue;
        this.actualCost = actualCost;
        this.budget = budget;
        this.payback = payback;
        this.startDate = startDate;
        this.endDate = endDate;
        // this.projectManager = projectManager;
    }

}
