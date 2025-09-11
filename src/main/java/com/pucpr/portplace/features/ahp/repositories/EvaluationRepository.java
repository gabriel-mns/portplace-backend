package com.pucpr.portplace.features.ahp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.ahp.entities.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
     
	public Page<Evaluation> findByEvaluationGroupId(long evaluationGroupId, PageRequest pageable);

	public Page<Evaluation> findByEvaluationGroupIdAndDisabledFalse(long evaluationGroupId, PageRequest pageable);

	@Query("""
		SELECT e FROM Evaluation e
		WHERE e.evaluationGroup.id = :evaluationGroupId
		AND LOWER(e.project.name) LIKE LOWER(CONCAT('%', :projectName, '%'))
		AND (:includeDisabled = true OR e.disabled = false)
		"""
	)
	public Page<Evaluation> findByEvaluationGroupIdAndProjectName(
		long evaluationGroupId, 
		String projectName,
		boolean includeDisabled,
		Pageable pageable
	);

	@Query("""
        SELECT DISTINCT e
        FROM Project p
            JOIN p.portfolio f
            JOIN f.activeScenario s
            JOIN s.evaluationGroup eg
            JOIN eg.evaluations e
        WHERE p.id = :projectId
        AND e.project = p
        AND e.disabled = false
    """)
    List<Evaluation> findEvaluationsByProjectId(@Param("projectId") Long projectId);
}
