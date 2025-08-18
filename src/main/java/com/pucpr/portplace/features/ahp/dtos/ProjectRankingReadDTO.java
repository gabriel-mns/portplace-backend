package com.pucpr.portplace.features.ahp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRankingReadDTO {

    private long projectId;
    private String name;
    private int position;
    private double totalScore;

}
