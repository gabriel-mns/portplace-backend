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
public class CriteriaComparisonCreateDTO {

    private long comparedCriterionId;
    private long referenceCriterionId;
    private ImportanceScale importanceScale;
    private long ahpId;

}
