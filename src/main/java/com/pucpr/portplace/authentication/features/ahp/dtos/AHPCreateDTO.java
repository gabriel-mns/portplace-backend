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
public class AHPCreateDTO {
    
    @NotNull
    private Long criteriaGroupId;
    private List<Long> projectIds;

}
