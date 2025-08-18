package com.pucpr.portplace.features.ahp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pucpr.portplace.features.ahp.entities.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
     
	// TODO: Implement paginated methods
	public Page<Evaluation> findByEvaluationGroupId(long evaluationGroupId, PageRequest pageable);

	public Page<Evaluation> findByEvaluationGroupIdAndDisabledFalse(long evaluationGroupId, PageRequest pageable);

	@Query("""
		SELECT e FROM Evaluation e
		WHERE e.evaluationGroup.id = :evaluationGroupId
		AND LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))
		AND (:includeDisabled = true OR e.disabled = false)
		"""
	)
	public Page<Evaluation> findByEvaluationGroupIdAndName(
		long evaluationGroupId, 
		String name,
		boolean includeDisabled,
		Pageable pageable
	);
}
