package com.pucpr.portplace.authentication.features.ahp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;

public interface CriteriaComparisonRepository extends JpaRepository<CriteriaComparison, Long> {
    
    // DON'T INCLUDE DISABLED CRITERIA COMPARISONS
    List<CriteriaComparison> findByComparedCriterionIdAndAhpIdAndDisabledFalse(long comparedCriterionId, long ahpId);
    
    List<CriteriaComparison> findByReferenceCriterionIdAndAhpIdAndDisabledFalse(long referenceCriterionId, long ahpId);
    
    List<CriteriaComparison> findByComparedCriterionIdAndReferenceCriterionIdAndAhpIdAndDisabledFalse(long referenceCriterionId, long comparedCriterionId, long ahpId);
    
    List<CriteriaComparison> findByAhpIdAndDisabledFalse(long ahpId);
    
    // INCLUDE DISABLED CRITERIA COMPARISONS
    List<CriteriaComparison> findByComparedCriterionIdAndAhpId(long comparedCriterionId, long ahpId);
    
    List<CriteriaComparison> findByReferenceCriterionIdAndAhpId(long referenceCriterionId, long ahpId);
    
    List<CriteriaComparison> findByComparedCriterionIdAndReferenceCriterionIdAndAhpId(long referenceCriterionId, long comparedCriterionId, long ahpId);
    
    List<CriteriaComparison> findByAhpId(long ahpId);

}
