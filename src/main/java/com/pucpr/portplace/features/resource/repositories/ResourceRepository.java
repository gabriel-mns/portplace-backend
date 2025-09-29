package com.pucpr.portplace.features.resource.repositories;

import com.pucpr.portplace.features.resource.dtos.resource.ResourceWithAvailableHoursProjection;
import com.pucpr.portplace.features.resource.entities.Resource;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query(
        """
        SELECT r FROM Resource r
        WHERE (:includeDisabled = true OR r.disabled = false)
            AND (LOWER(r.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
            AND (:status IS NULL OR r.status IN :status)
        """
    )
    Page<Resource> findByFilters(
        List<ResourceStatusEnum> status,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    );

    @Query(
        """
        SELECT r FROM Resource r
        WHERE (:includeDisabled = true OR r.disabled = false)
            AND (LOWER(r.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
            AND (:status IS NULL OR r.status IN :status)
            AND (:positionId IS NULL OR r.position.id = :positionId)
        """
    )
    List<Resource> findByFiltersUnpaged(
        Long positionId,
        List<ResourceStatusEnum> status, 
        String searchQuery,
        boolean includeDisabled
    );

    @Query(value = """
        SELECT r.id,
            r.name,
            r.description,
            r.daily_hours,
            r.status,
            r.position_id,
            r.created_by,
            r.last_modified_by,
            TO_CHAR(r.created_at, 'DD/MM/YYYY HH24:MI:SS') AS created_at,
            TO_CHAR(r.last_modified_at, 'DD/MM/YYYY HH24:MI:SS') AS last_modified_at,
            r.disabled,
            (
                (r.daily_hours * (
                    SELECT COUNT(*) 
                    FROM generate_series(CAST(:startDate AS date), CAST(:endDate AS date), interval '1 day') d
                    WHERE EXTRACT(ISODOW FROM d) < 6
                ))
                -
                COALESCE((
                    SELECT SUM(a.daily_hours * (
                        SELECT COUNT(*) 
                        FROM generate_series(GREATEST(a.start_date, :startDate), LEAST(a.end_date, :endDate), interval '1 day') d
                        WHERE EXTRACT(ISODOW FROM d) < 6
                    ))
                    FROM allocations a
                    WHERE a.resource_id = r.id
                ), 0)
            ) AS available_hours,
            (
                SELECT COUNT(DISTINCT ar.project_id)
                FROM allocations a
                    JOIN allocation_requests ar ON a.allocation_request_id = ar.id
                WHERE a.resource_id = r.id
                    AND a.start_date <= :endDate
                    AND a.end_date >= :startDate
            ) AS related_projects_count
        FROM resources r
        WHERE (:includeDisabled = true OR r.disabled = false)
        AND (LOWER(r.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        AND (:status IS NULL OR r.status IN (:status))
        ORDER BY available_hours ASC
    """,
    countQuery = """
        SELECT COUNT(*) 
        FROM resources r
        WHERE (:includeDisabled = true OR r.disabled = false)
        AND (LOWER(r.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        AND (:status IS NULL OR r.status IN (:status))
    """,
    nativeQuery = true)
    Page<ResourceWithAvailableHoursProjection> findByFiltersWithAvailableHoursOrderedByItAsc(
        @Param("status") List<String> status,
        @Param("searchQuery") String searchQuery,
        @Param("includeDisabled") boolean includeDisabled,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );

    @Query(value = """
        SELECT r.id,
            r.name,
            r.description,
            r.daily_hours,
            r.status,
            r.position_id,
            r.created_by,
            r.last_modified_by,
            TO_CHAR(r.created_at, 'DD/MM/YYYY HH24:MI:SS') AS created_at,
            TO_CHAR(r.last_modified_at, 'DD/MM/YYYY HH24:MI:SS') AS last_modified_at,
            r.disabled,
            (
                (r.daily_hours * (
                    SELECT COUNT(*) 
                    FROM generate_series(CAST(:startDate AS date), CAST(:endDate AS date), interval '1 day') d
                    WHERE EXTRACT(ISODOW FROM d) < 6
                ))
                -
                COALESCE((
                    SELECT SUM(a.daily_hours * (
                        SELECT COUNT(*) 
                        FROM generate_series(GREATEST(a.start_date, :startDate), LEAST(a.end_date, :endDate), interval '1 day') d
                        WHERE EXTRACT(ISODOW FROM d) < 6
                    ))
                    FROM allocations a
                    WHERE a.resource_id = r.id
                ), 0)
            ) AS available_hours,
            (
                SELECT COUNT(DISTINCT ar.project_id)
                FROM allocations a
                    JOIN allocation_requests ar ON a.allocation_request_id = ar.id
                WHERE a.resource_id = r.id
                    AND a.start_date <= :endDate
                    AND a.end_date >= :startDate
            ) AS related_projects_count
        FROM resources r
        WHERE (:includeDisabled = true OR r.disabled = false)
        AND (LOWER(r.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        AND (:status IS NULL OR r.status IN (:status))
        ORDER BY available_hours DESC
    """,
    countQuery = """
        SELECT COUNT(*) 
        FROM resources r
        WHERE (:includeDisabled = true OR r.disabled = false)
        AND (LOWER(r.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        AND (:status IS NULL OR r.status IN (:status))
    """,
    nativeQuery = true)
    Page<ResourceWithAvailableHoursProjection> findByFiltersWithAvailableHoursOrderedByItDesc(
        @Param("status") List<String> status,
        @Param("searchQuery") String searchQuery,
        @Param("includeDisabled") boolean includeDisabled,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );

    @Query(value = """
        SELECT r.id,
            r.name,
            r.description,
            r.daily_hours,
            r.status,
            r.position_id,
            r.created_by,
            r.last_modified_by,
            TO_CHAR(r.created_at, 'DD/MM/YYYY HH24:MI:SS') AS created_at,
            TO_CHAR(r.last_modified_at, 'DD/MM/YYYY HH24:MI:SS') AS last_modified_at,
            r.disabled,
            (
                (r.daily_hours * (
                    SELECT COUNT(*) 
                    FROM generate_series(CAST(:startDate AS date), CAST(:endDate AS date), interval '1 day') d
                    WHERE EXTRACT(ISODOW FROM d) < 6
                ))
                -
                COALESCE((
                    SELECT SUM(a.daily_hours * (
                        SELECT COUNT(*) 
                        FROM generate_series(GREATEST(a.start_date, :startDate), LEAST(a.end_date, :endDate), interval '1 day') d
                        WHERE EXTRACT(ISODOW FROM d) < 6
                    ))
                    FROM allocations a
                    WHERE a.resource_id = r.id
                ), 0)
            ) AS available_hours,
            (
                SELECT COUNT(DISTINCT ar.project_id)
                FROM allocations a
                    JOIN allocation_requests ar ON a.allocation_request_id = ar.id
                WHERE a.resource_id = r.id
                    AND a.start_date <= :endDate
                    AND a.end_date >= :startDate
            ) AS related_projects_count
        FROM resources r
        WHERE (:includeDisabled = true OR r.disabled = false)
        AND (LOWER(r.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        AND (:status IS NULL OR r.status IN (:status))
    """,
    countQuery = """
        SELECT COUNT(*) 
        FROM resources r
        WHERE (:includeDisabled = true OR r.disabled = false)
        AND (LOWER(r.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
        AND (:status IS NULL OR r.status IN (:status))
    """,
    nativeQuery = true)
    Page<ResourceWithAvailableHoursProjection> findByFiltersWithAvailableHours(
        @Param("status") List<String> status,
        @Param("searchQuery") String searchQuery,
        @Param("includeDisabled") boolean includeDisabled,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );


}
