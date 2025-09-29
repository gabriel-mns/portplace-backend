package com.pucpr.portplace.features.resource.dtos.resource;

import com.pucpr.portplace.features.resource.dtos.position.PositionReadDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceReadDTO {
    
    private long id;
    @NotBlank
    private String name;
    private String description;
    private Integer dailyHours;
    private String status;
    
    //Calculated Fields
    // private int relatedProjectsCount;
    private int availableHours;
    
    //Relationships
    private PositionReadDTO position;
    
    //Auditable Fields
    private String createdBy;
    private String lastModifiedBy;
    private String createdAt;
    private String lastModifiedAt;
    private boolean disabled;

}
