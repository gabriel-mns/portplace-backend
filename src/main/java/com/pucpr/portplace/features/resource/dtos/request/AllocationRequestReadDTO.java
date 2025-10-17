package com.pucpr.portplace.features.resource.dtos.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.resource.dtos.allocation.AllocationReadDTO;
import com.pucpr.portplace.features.resource.dtos.position.PositionReadDTO;
import com.pucpr.portplace.features.resource.enums.AllocationRequestStatusEnum;
import com.pucpr.portplace.features.resource.enums.PriorityEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AllocationRequestReadDTO {
    
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    private AllocationRequestStatusEnum status;
    private PriorityEnum priority;
    private int dailyHours;

    //Relationships
    private PositionReadDTO position;
    private AllocationReadDTO allocation;
    private ProjectReadDTO project;

    //Auditable Fields
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;
    private boolean disabled;
    
}
