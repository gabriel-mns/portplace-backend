package com.pucpr.portplace.features.portfolio.entities;

import java.util.List;

import org.hibernate.annotations.Formula;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.portfolio.enums.CommunicationMethodsEnum;
import com.pucpr.portplace.features.portfolio.enums.PeriodicityEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;

    //Details page
    private String infosAndDocs;
    private String discussionTopic;
    private String reason;
    private PeriodicityEnum periodicity;
    private List<CommunicationMethodsEnum> communicationMethods;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @OneToMany(mappedBy = "event")
    private List<EventParticipant> participants;

    //Calculated Fields
    @Formula(value = "(SELECT COUNT(ep.id) FROM event_participants ep WHERE ep.event_id = id)")
    private int participantsCount;

}
