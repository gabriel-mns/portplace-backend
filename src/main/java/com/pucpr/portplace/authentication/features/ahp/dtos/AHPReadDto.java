package com.pucpr.portplace.authentication.features.ahp.dtos;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectReadDTO;

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
public class AHPReadDto {
    
    private long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    private boolean disabled;
    private List<ProjectReadDTO> projects;
    private List<CriterionReadDto> criteria;
    private List<CriteriaComparisonReadDto> criteriaComparisons;
    private List<EvaluationReadDto> evaluations;

}
