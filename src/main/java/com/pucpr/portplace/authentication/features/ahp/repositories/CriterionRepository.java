package com.pucpr.portplace.authentication.features.ahp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;

public interface CriterionRepository extends JpaRepository<Criterion, Long> {
    
    List<Criterion> findByCriteriaGroupId(Long criteriaGroupId);

    List<Criterion> findByCriteriaGroupIdAndDisabledFalse(Long criteriaGroupId);

}
