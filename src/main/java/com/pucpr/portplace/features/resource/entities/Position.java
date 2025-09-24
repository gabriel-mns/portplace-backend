package com.pucpr.portplace.features.resource.entities;

import java.util.List;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "positions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Position extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ResourceStatusEnum status;

    //Calculated Fields
    // private int resourcesCount;

    //Relationships
    @OneToMany(mappedBy = "position")
    private List<AllocationRequest> allocationRequests;

}
