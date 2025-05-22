package com.pucpr.portplace.authentication.features.ahp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
     // TODO: Implement paginated methods
}
