package com.pucpr.portplace.features.resource.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    //Auditable Fields
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;
    private boolean disabled;
    
}
