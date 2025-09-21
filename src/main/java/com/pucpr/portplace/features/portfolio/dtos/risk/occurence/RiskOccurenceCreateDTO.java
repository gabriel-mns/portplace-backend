package com.pucpr.portplace.features.portfolio.dtos.risk.occurence;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RiskOccurenceCreateDTO {

    private String description;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateOfOccurrence;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime solvedAt;

    //Relationships
    private Long riskId;

}
