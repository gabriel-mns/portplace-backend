package com.pucpr.portplace.features.resource.dtos.allocation;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceReadDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AllocationReadDTO {
    
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    private int dailyHours;

    //Relationships
    private ResourceReadDTO resource;
    private Long allocationRequestId;
        // Fields from Allocation Request
        private String requestedBy;
        private String projectName;
        private PriorityEnum priority;



    //Auditable Fields
    private String createdBy;
    private String lastModifiedBy;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;

}
