package com.pucpr.portplace.authentication.features.ahp.dtos;

import java.util.List;

import jakarta.validation.constraints.NotNull;
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
public class AHPUpdateDTO {
    
    private Long id;
    private String name;
    private String description;
    @NotNull
    private Long criteriaGroupId;
    private Long strategyId;
    private List<Long> projectIds; // REVIEW: Check if projects will be added later or if they are required at creation time

}
