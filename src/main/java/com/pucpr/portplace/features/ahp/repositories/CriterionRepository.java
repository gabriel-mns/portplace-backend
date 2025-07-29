package com.pucpr.portplace.features.ahp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.ahp.entities.Criterion;

public interface CriterionRepository extends JpaRepository<Criterion, Long> {
    
    Page<Criterion> findByCriteriaGroupId(Long criteriaGroupId, Pageable pageable);

    Page<Criterion> findByCriteriaGroupIdAndDisabledFalse(Long criteriaGroupId, Pageable pageable);

}
