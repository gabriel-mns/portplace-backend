package com.pucpr.portplace.authentication.features.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evaluations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Evaluation {

    @Id
    private long id;
    private int score;
    private Project project;
    // TODO: Create criteria CRUD
    // private Criteria criteria;

}
