package com.pucpr.portplace.authentication.features.ahp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaComparison;

public interface CriteriaComparisonRepository extends JpaRepository<CriteriaComparison, Long> {
    
    // DON'T INCLUDE DISABLED CRITERIA COMPARISONS
    List<CriteriaComparison> findByComparedCriterionIdAndCriteriaGroupIdAndDisabledFalse(long comparedCriterionId, long criteriaGroupId);
    
    List<CriteriaComparison> findByReferenceCriterionIdAndCriteriaGroupIdAndDisabledFalse(long referenceCriterionId, long criteriaGroupId);
    
    List<CriteriaComparison> findByComparedCriterionIdAndReferenceCriterionIdAndCriteriaGroupIdAndDisabledFalse(long referenceCriterionId, long comparedCriterionId, long criteriaGroupId);
    
    List<CriteriaComparison> findByCriteriaGroupIdAndDisabledFalse(long criteriaGroupId);
    
    // INCLUDE DISABLED CRITERIA COMPARISONS
    List<CriteriaComparison> findByComparedCriterionIdAndCriteriaGroupId(long comparedCriterionId, long criteriaGroupId);
    
    List<CriteriaComparison> findByReferenceCriterionIdAndCriteriaGroupId(long referenceCriterionId, long criteriaGroupId);
    
    List<CriteriaComparison> findByComparedCriterionIdAndReferenceCriterionIdAndCriteriaGroupId(long referenceCriterionId, long comparedCriterionId, long criteriaGroupId);
    
    List<CriteriaComparison> findByCriteriaGroupId(long criteriaGroupId);

}
