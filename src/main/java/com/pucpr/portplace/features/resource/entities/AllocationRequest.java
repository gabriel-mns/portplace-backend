package com.pucpr.portplace.features.resource.entities;

import java.time.LocalDate;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.resource.enums.AllocationRequestStatusEnum;
import com.pucpr.portplace.features.resource.enums.PriorityEnum;

import jakarta.persistence.Entity;
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
@Table(name = "allocation_requests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AllocationRequest extends AuditableEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int dailyHours;
    private PriorityEnum priority;
    private AllocationRequestStatusEnum status;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
}
