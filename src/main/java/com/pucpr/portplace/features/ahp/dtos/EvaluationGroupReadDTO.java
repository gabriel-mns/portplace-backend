package com.pucpr.portplace.features.ahp.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.ahp.enums.EvaluationGroupStatusEnum;

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
public class EvaluationGroupReadDTO {
    
    private long id;
    private String name;
    private String description;
    private EvaluationGroupStatusEnum status;

    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    
    private CriteriaGroupReadDTO criteriaGroup;
    private List<EvaluationReadDTO> evaluations;
    // private UserReadDto lastUpdatedBy;


}
