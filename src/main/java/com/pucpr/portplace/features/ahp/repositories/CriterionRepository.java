package com.pucpr.portplace.features.ahp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pucpr.portplace.features.ahp.entities.Criterion;

public interface CriterionRepository extends JpaRepository<Criterion, Long> {

    @Query("""
        SELECT DISTINCT c FROM Criterion c
        WHERE c.criteriaGroup.id = :criteriaGroupId
        AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:includeDisabled = true OR c.disabled = false)
    """)
    Page<Criterion> findByCriteriaGroupId(
        Long criteriaGroupId,
        Boolean includeDisabled,
        String name,
        Pageable pageable
    );

    
    @Query("""
        SELECT DISTINCT c FROM Criterion c
        JOIN c.strategicObjectives o
        WHERE (:objectiveId IS NULL OR o.id = :objectiveId)
        AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:includeDisabled = true OR c.disabled = false)
    """)
    Page<Criterion> findByStrategicObjectiveId(
        Long objectiveId,
        Boolean includeDisabled,
        String name,
        Pageable pageable
    );

}
