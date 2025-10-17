package com.pucpr.portplace.features.portfolio.dtos.risk.occurence;

import java.time.LocalDateTime;

import com.pucpr.portplace.core.validation.constraints.dateTimeRange.ValidDateTimeRange;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ValidDateTimeRange(
    startField = "dateOfOccurrence",
    endField = "solvedAt"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RiskOccurenceCreateDTO {

    private String description;
    @NotNull
    private LocalDateTime dateOfOccurrence;
    private LocalDateTime solvedAt;
    private Boolean followedPreventionPlan;
    private String preventionActions;
    private Boolean followedContingencyPlan;
    private String contingencyActions;

    //Relationships
    private Long riskId;

}
