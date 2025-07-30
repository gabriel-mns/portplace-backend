package com.pucpr.portplace.features.ahp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.features.ahp.entities.Criterion;

public interface CriteriaComparisonRepository extends JpaRepository<CriteriaComparison, Long> {
    
    // DON'T INCLUDE DISABLED CRITERIA COMPARISONS
    List<CriteriaComparison> findByComparedCriterionIdAndCriteriaGroupIdAndDisabledFalse(long comparedCriterionId, long criteriaGroupId);
    
    List<CriteriaComparison> findByReferenceCriterionIdAndCriteriaGroupIdAndDisabledFalse(long referenceCriterionId, long criteriaGroupId);
    
    List<CriteriaComparison> findByComparedCriterionIdAndReferenceCriterionIdAndCriteriaGroupIdAndDisabledFalse(long referenceCriterionId, long comparedCriterionId, long criteriaGroupId);
    
    List<CriteriaComparison> findByCriteriaGroupIdAndDisabledFalse(long criteriaGroupId);
    
    // INCLUDE DISABLED CRITERIA COMPARISONS
    // List<CriteriaComparison> findByComparedCriterionIdAndCriteriaGroupId(long comparedCriterionId, long criteriaGroupId);
    
    // List<CriteriaComparison> findByReferenceCriterionIdAndCriteriaGroupId(long referenceCriterionId, long criteriaGroupId);
    
    // List<CriteriaComparison> findByComparedCriterionIdAndReferenceCriterionIdAndCriteriaGroupId(long referenceCriterionId, long comparedCriterionId, long criteriaGroupId);
    
    @Query("""
        SELECT cc FROM CriteriaComparison cc
        WHERE cc.criteriaGroup.id = :criteriaGroupId
            AND (
                (:criterion1Id IS NULL OR :criterion2Id IS NULL)
                OR (
                    (cc.referenceCriterion.id = :criterion1Id AND cc.comparedCriterion.id = :criterion2Id)
                    OR
                    (cc.referenceCriterion.id = :criterion2Id AND cc.comparedCriterion.id = :criterion1Id)
                )
            )
            AND cc.disabled = :includeDisabled
    """)
    Page<CriteriaComparison> findComparisons(
        Long criteriaGroupId,
        Long criterion1Id,
        Long criterion2Id,
        boolean includeDisabled,
        Pageable pageable
    );


    Page<CriteriaComparison> findByCriteriaGroupId(long criteriaGroupId, Pageable pageable);

    // VALIDATIONS
    @Query(
        """
            SELECT 
                CASE WHEN COUNT(c) > 0 THEN true ELSE false END 
            FROM 
                CriteriaComparison c 
            WHERE 
               ((c.comparedCriterion = :criterion1 AND c.referenceCriterion = :criterion2) OR
                (c.comparedCriterion = :criterion2 AND c.referenceCriterion = :criterion1))
            AND c.disabled = false
        """)
    boolean existsComparisonBetweenCriteria(Criterion criterion1, Criterion criterion2);

    @Query(
        """
            SELECT c
            FROM CriteriaComparison c
            WHERE 
                (
                    (c.comparedCriterion = :criterion1 AND c.referenceCriterion = :criterion2) OR
                    (c.comparedCriterion = :criterion2 AND c.referenceCriterion = :criterion1)
                )
                AND c.disabled = false
        """)
    CriteriaComparison findActiveComparisonBetweenCriteria(Criterion criterion1, Criterion criterion2);

}
