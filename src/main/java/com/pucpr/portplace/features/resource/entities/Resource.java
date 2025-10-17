package com.pucpr.portplace.features.resource.entities;

import java.util.List;

import org.hibernate.annotations.Formula;

import com.pucpr.portplace.core.entities.AuditableEntity;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resources")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Resource extends AuditableEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int dailyHours;
    @Enumerated(EnumType.STRING)
    private ResourceStatusEnum status;

    //calculated fields
    @Formula("""
        (
            SELECT COUNT(DISTINCT pr.id)
            FROM projects pr
            JOIN allocation_requests ar ON ar.project_id = pr.id
            JOIN allocations al ON al.allocation_request_id = ar.id
            WHERE al.resource_id = id
        )
    """)
    private int relatedProjectsCount;

    //Realtionships
    @ManyToOne
    private Position position;
    @OneToMany(mappedBy = "resource")
    private List<Allocation> allocation;

}
