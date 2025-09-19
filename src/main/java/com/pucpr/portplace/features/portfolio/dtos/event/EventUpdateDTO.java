package com.pucpr.portplace.features.portfolio.dtos.event;

import java.util.List;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
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
public class EventUpdateDTO {
    
    private String infosAndDocs;
    private String discussionTopic;
    private String reason;
    @ValidEnum(enumClass = PeriodicityEnum.class)
    private String periodicity;
    @ValidEnum(enumClass = CommunicationMethodsEnum.class)
    private List<String> communicationMethods;

}
