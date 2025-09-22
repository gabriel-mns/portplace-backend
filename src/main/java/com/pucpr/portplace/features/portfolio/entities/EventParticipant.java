package com.pucpr.portplace.features.portfolio.entities;

import com.pucpr.portplace.core.entities.AuditableEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_participants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventParticipant extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean responsible;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "stakeholder_id", nullable = false)
    private Stakeholder stakeholder;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

}
