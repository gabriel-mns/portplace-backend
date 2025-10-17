package com.pucpr.portplace.features.resource.entities;

import java.time.LocalDate;

import org.hibernate.annotations.Formula;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.resource.enums.AllocationStatusEnum;
import com.pucpr.portplace.features.resource.enums.PriorityEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "allocations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Allocation extends AuditableEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int dailyHours;
    private PriorityEnum priority;
    private boolean cancelled = false;

    // Calculated fields
    @Enumerated(EnumType.STRING)
    @Formula(
        """
        (
            CASE 
                WHEN end_date < CURRENT_DATE THEN 'COMPLETED'
                WHEN cancelled = true THEN 'CANCELLED'
                ELSE 'ALLOCATED'
            END
        )     
        """
    )
    private AllocationStatusEnum status;

    //Relationships
    @OneToOne
    @JoinColumn(name = "allocation_request_id")
    private AllocationRequest allocationRequest;
    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;
    
}
