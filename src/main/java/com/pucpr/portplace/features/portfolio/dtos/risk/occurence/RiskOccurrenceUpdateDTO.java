package com.pucpr.portplace.features.portfolio.dtos.risk.occurence;

import java.time.LocalDateTime;

import com.pucpr.portplace.core.validation.constraints.dateTimeRange.ValidDateTimeRange;

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
public class RiskOccurrenceUpdateDTO {

    private String description;
    private LocalDateTime dateOfOccurrence;
    private LocalDateTime solvedAt;

}
