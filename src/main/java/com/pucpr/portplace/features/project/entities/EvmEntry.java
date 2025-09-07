package com.pucpr.portplace.features.project.entities;

import com.pucpr.portplace.core.entities.AuditableEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evm_entries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvmEntry extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double plannedValue;
    private double actualCost;
    private double percentComplete;
    private short month;
    private short year;

    @Transient
    public double getEarnedValue() {
        return plannedValue * (percentComplete / 100.0);
    }

    // Relationships
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
