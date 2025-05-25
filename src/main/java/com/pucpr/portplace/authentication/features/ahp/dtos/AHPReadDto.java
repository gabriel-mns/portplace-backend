package com.pucpr.portplace.authentication.features.ahp.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AHPReadDTO {
    
    private long id;
    @JsonIdentityReference(alwaysAsId = true)
    private long criteriaGroupId;
    private List<EvaluationReadDTO> evaluations;
    private boolean disabled;
    // private LocalDateTimo> evaluatione createdAt;
    // private LocalDateTime lastUpdatedAt;
    // private UserReadDto lastUpdatedBy;


}
