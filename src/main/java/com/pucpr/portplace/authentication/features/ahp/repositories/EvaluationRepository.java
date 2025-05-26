package com.pucpr.portplace.authentication.features.ahp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
     
     // TODO: Implement paginated methods
     public List<Evaluation> findByAhpId(long ahpId);

     public List<Evaluation> findByAhpIdAndDisabledFalse(long ahpId);
     

}
