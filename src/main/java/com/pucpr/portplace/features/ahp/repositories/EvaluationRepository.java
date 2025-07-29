package com.pucpr.portplace.features.ahp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.ahp.entities.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
     
     // TODO: Implement paginated methods
     public Page<Evaluation> findByAhpId(long ahpId, PageRequest pageable);

     public Page<Evaluation> findByAhpIdAndDisabledFalse(long ahpId, PageRequest pageable);
     

}
