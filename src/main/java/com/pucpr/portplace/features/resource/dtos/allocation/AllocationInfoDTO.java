package com.pucpr.portplace.features.resource.dtos.allocation;

import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.resource.dtos.resource.ResourceReadDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllocationInfoDTO {
    private ProjectReadDTO project;
    private ResourceReadDTO resource;
    private int dailyHours;
}