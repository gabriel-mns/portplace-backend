package com.pucpr.portplace.features.portfolio.dtos.event;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pucpr.portplace.features.portfolio.enums.CommunicationMethodsEnum;
import com.pucpr.portplace.features.portfolio.enums.PeriodicityEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventReadDTO {
    
    private long id;
    private String name;
    private String description;
    private String infosAndDocs;
    private String discussionTopic;
    private String reason;
    private PeriodicityEnum periodicity;
    private List<CommunicationMethodsEnum> communicationMethods;

    //Relationships
    private long portfolioId;
    private int participantsCount;

    //Auditing Fields
    private boolean disabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;

}
