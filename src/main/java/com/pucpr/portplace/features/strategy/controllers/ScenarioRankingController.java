package com.pucpr.portplace.features.strategy.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import com.pucpr.portplace.features.strategy.dtos.ScenarioRankingReadDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioRankingUpdateDTO;
import com.pucpr.portplace.features.strategy.services.ScenarioRankingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "ScenarioRanking", description = "Related to the ScenarioRanking CRUD operations")
@RestController
@RequestMapping("/strategies/{strategyId}/scenarios/{scenarioId}/rankings")
@AllArgsConstructor
public class ScenarioRankingController {

    private ScenarioRankingService rankingService;
    
    //UPDATE
    @PutMapping("/{rankingId}")
    public ResponseEntity<ScenarioRankingReadDTO> updateScenarioRanking(
        @PathVariable long scenarioId,
        @PathVariable long rankingId,
        @RequestBody @Valid ScenarioRankingUpdateDTO dto
    ){

        ScenarioRankingReadDTO response = rankingService.updateScenarioRanking(dto, scenarioId, rankingId);

        return ResponseEntity.ok().body(response);

    }

    //READ
    @GetMapping
    public ResponseEntity<Page<ScenarioRankingReadDTO>> getAllRankingsByScenarioId(
        @PathVariable long scenarioId,
        @RequestParam(defaultValue = "", required = false) String searchQuery,
        @RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="10") int size,
        @RequestParam(defaultValue = "statusOrder", required = false) String sortBy,
        @RequestParam(defaultValue="asc") String sortDir
    ){

        Sort sort;
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;


        if (sortBy == null || sortBy.isEmpty()) {
            sort = Sort.by(Sort.Order.asc("status"), Sort.Order.desc("totalScore"));
        } else {
            sort = Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ScenarioRankingReadDTO> response = rankingService.getAllRankingsByScenarioId(scenarioId, searchQuery, pageable);

        return ResponseEntity.ok().body(response);

    }

}
