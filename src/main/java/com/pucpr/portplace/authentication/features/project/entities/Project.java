package com.pucpr.portplace.authentication.features.project.entities;

import java.util.Date;
import java.util.List;

import com.pucpr.portplace.authentication.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.authentication.features.user.entities.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
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
    private ProjectStatusEnum status;
    private double earnedValue;
    private double plannedValue;
    private double actualCost;
    private double budget;
    private double payback;
    private Date startDate;
    private Date endDate;
    private User projectManager;
    @OneToMany
    private List<Evaluation> evaluations;
    // TODO: Create attachments table and add a list of attachments to the project
    // private List<Attachment> attachments;
    // TODO: Uncomment when Portfolio is implemented
    // private Portfolio portfolio;

    public Project(String name, String description, ProjectStatusEnum status, double earnedValue, double plannedValue,
            double actualCost, double budget, double payback, Date startDate, Date endDate, User projectManager) {
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
        this.projectManager = projectManager;
    }

}
