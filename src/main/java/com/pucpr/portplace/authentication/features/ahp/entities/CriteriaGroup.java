package com.pucpr.portplace.authentication.features.ahp.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.pucpr.portplace.authentication.features.user.entities.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "criteria_groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CriteriaGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    private String description;

    @Builder.Default
    private boolean disabled =  false;

    //TODO: Create strategy objectives CRUD
    //List<StrategyObjective> strategyObjectives;
    @ManyToOne
    private Strategy strategy;
    
    // Audit fields
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
    
    @JsonIdentityReference(alwaysAsId = true)
    @LastModifiedBy
    private User lastModifiedBy;
    
    @CreatedDate
    private LocalDateTime createdAt;


    // Relationships
    @OneToMany(mappedBy = "criteriaGroup")
    private List<Criterion> criteria;

    @OneToMany(mappedBy = "criteriaGroup")
    // @OneToMany(mappedBy = "criteriaGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CriteriaComparison> criteriaComparisons;

}
