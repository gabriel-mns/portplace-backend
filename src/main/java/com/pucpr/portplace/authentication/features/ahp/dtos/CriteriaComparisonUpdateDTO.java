package com.pucpr.portplace.authentication.features.ahp.dtos;

import com.pucpr.portplace.authentication.features.ahp.enums.ImportanceScale;

import lombok.AllArgsConstructor;
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
