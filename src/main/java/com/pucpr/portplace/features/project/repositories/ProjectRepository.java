package com.pucpr.portplace.features.project.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.project.enums.ProjectStatusEnum;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    @Query("""
        SELECT p
        FROM Project p
        WHERE (:projectManagerId IS NULL OR p.projectManager.id = :projectManagerId)
            AND (:portfolioId IS NULL OR p.portfolio.id = :portfolioId)
            AND (:includeDisabled = true OR p.disabled = false)
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:status IS NULL OR p.status IN :status)
    """)
    Page<Project> findByFilters(
        @Param("projectManagerId") Long projectManagerId,
        @Param("portfolioId") Long portfolioId,
        @Param("name") String name,
        @Param("status") List<ProjectStatusEnum> status,
        @Param("includeDisabled") boolean includeDisabled,
        Pageable pageable
    );



    @Query("""
        SELECT DISTINCT p FROM Project p
        JOIN p.scenarioRankings sr
        JOIN sr.scenario s
        JOIN s.evaluationGroup eg
        JOIN eg.criteriaGroup cg
        JOIN cg.criteria c
        JOIN c.strategicObjectives o
        WHERE o.id = :objectiveId
          AND sr.status NOT IN (com.pucpr.portplace.features.strategy.enums.ScenarioRankingStatusEnum.MANUALLY_EXCLUDED,
                               com.pucpr.portplace.features.strategy.enums.ScenarioRankingStatusEnum.EXCLUDED)
          AND p.disabled = false
          AND s.disabled = false
          AND eg.disabled = false
          AND cg.disabled = false
          AND c.disabled = false
          AND o.disabled = false
          AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
    """)
    Page<Project> findByObjectiveId(
        @Param("objectiveId") Long objectiveId,
        @Param("searchQuery") String searchQuery,
        Pageable pageable
    );

}
