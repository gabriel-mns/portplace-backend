package com.pucpr.portplace.features.project.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvmEntryReadDTO {
    
    private long id;
    private double plannedValue;
    private double actualCost;
    private double percentComplete;
    private double earnedValue;
    private int month;
    private int year;

    // Relationships
    private long projectId;

    // Auditable fields
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastModifiedAt;
    private boolean disabled;

}
