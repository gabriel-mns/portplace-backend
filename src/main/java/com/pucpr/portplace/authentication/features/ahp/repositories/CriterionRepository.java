package com.pucpr.portplace.authentication.features.ahp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;

public interface CriterionRepository extends JpaRepository<Criterion, Long> {
    
    List<Criterion> findByAhpId(Long ahpId);

    List<Criterion> findByAhpIdAndDisabledFalse(Long ahpId);

}
