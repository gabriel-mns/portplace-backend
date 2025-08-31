package com.pucpr.portplace.features.ahp.dtos;

import java.util.List;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.ahp.enums.CriteriaGroupStatusEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CriteriaGroupUpdateDTO {
    
    @NotBlank
    private String name;
    private String description;
    @ValidEnum(enumClass = CriteriaGroupStatusEnum.class)
    private String status;
    private List<Long> criteriaIdList;
    
}
