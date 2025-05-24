package com.pucpr.portplace.authentication.features.ahp.dtos;

import com.pucpr.portplace.authentication.features.ahp.enums.ImportanceScale;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaComparisonUpdateDTO {

    private Long id;
    private ImportanceScale importanceScale;

}
