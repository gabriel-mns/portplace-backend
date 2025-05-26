package com.pucpr.portplace.authentication.features.ahp.dtos;

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
public class EvaluationUpdateDTO {
    
    private long id;
    @NotNull
    @Min(0)
    @Max(1000)
    private int score;

}
