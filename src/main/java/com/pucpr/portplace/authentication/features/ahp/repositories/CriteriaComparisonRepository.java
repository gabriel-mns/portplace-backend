package com.pucpr.portplace.authentication.features.ahp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;

public interface CriteriaComparisonRepository extends JpaRepository<CriteriaComparison, Long> {
    
}
