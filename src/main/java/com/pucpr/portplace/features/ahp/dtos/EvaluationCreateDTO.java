package com.pucpr.portplace.features.ahp.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EvaluationCreateDTO {

    @NotNull
    @Min(0)
    @Max(1000)
    private int score;
    private long projectId;
    private long criterionId;
    private Long ahpId; // Usando Long (wrapper) permite valor nulo, tornando opcional

}
