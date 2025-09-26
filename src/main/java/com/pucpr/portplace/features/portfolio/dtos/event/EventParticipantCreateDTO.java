package com.pucpr.portplace.features.portfolio.dtos.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventParticipantCreateDTO {
    
    @NotNull
    private Long stakeholderId;
    private Long eventId;
    private boolean responsible = false;

}
