package com.pucpr.portplace.authentication.features.ahp.entities;

import java.util.List;

import com.pucpr.portplace.authentication.features.project.entities.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AHP {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    @JoinTable(
        name = "ahp_projects",
        joinColumns = @JoinColumn(name = "ahp_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects;

    @OneToMany(mappedBy = "ahp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Criterion> criteria;

    @OneToMany(mappedBy = "ahp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CriteriaComparison> criteriaComparisons;

    @OneToMany(mappedBy = "ahp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations;


}
