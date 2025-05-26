package com.pucpr.portplace.authentication.features.ahp.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.authentication.features.ahp.enums.ImportanceScale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaComparisonReadDTO {

    private Long id;
    private long comparedCriterionId;
    private long referenceCriterionId;
    private ImportanceScale importanceScale;
    private long criteriaGroupId;
    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    // private UserReadDto lastUpdatedBy; // Uncomment if needed

}
