package com.pucpr.portplace.features.ahp.dtos;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveReadDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriterionReadDTO {

    private Long id;
    @NotBlank
    @NotNull
    private String name;
    private String description;
    private long criteriaGroupId;
    private double weight;
    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    // private UserReadDto lastUpdatedBy; // Uncomment if needed

    private int relatedStrategicObjectivesCount;
    private List<StrategicObjectiveReadDTO> strategicObjectives;

}
