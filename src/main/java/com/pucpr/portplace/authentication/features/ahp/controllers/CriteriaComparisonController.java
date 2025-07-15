package com.pucpr.portplace.authentication.features.ahp.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.authentication.features.ahp.services.CriteriaComparisonService;

@RestController
@RequestMapping(StrategyPaths.CRITERIA_COMPARISONS)
// @RequestMapping("/strategies/{strategyId}/criteria-groups/{criteriaGroupId}/criteria-comparisons")
public class CriteriaComparisonController {

    @Autowired
    private CriteriaComparisonService criterionComparisonService;
    
    // CREATE
    @PostMapping
    public ResponseEntity<CriteriaComparisonReadDTO> createCriterionComparison(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @RequestBody CriteriaComparisonCreateDTO criteriaComparisonCreateDTO) {
        
        CriteriaComparisonReadDTO createdCriteriaComparison = criterionComparisonService.createCriteriaComparison(strategyId, criteriaGroupId, criteriaComparisonCreateDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{criteriaComparisonId}")
            .buildAndExpand(createdCriteriaComparison.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdCriteriaComparison);
    
    }

    // UPDATE
    @PutMapping("/{criteriaComparisonId}")
    public ResponseEntity<CriteriaComparisonReadDTO> updateCriterionComparison(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @PathVariable long criteriaComparisonId, @RequestBody CriteriaComparisonUpdateDTO criteriaComparisonUpdateDTO) {
        
        CriteriaComparisonReadDTO updatedCriteriaComparison = criterionComparisonService.updateCriteriaComparison(criteriaComparisonId, criteriaComparisonUpdateDTO);

        return ResponseEntity.ok().body(updatedCriteriaComparison);
    
    }

    // DELETE
    @DeleteMapping("/{criteriaComparisonId}")
    public ResponseEntity<Void> disableCriterionComparison(@PathVariable long strategyId, @PathVariable long criteriaComparisonId) {
        
        criterionComparisonService.disableCriteriaComparison(criteriaComparisonId);
        
        return ResponseEntity.noContent().build();
    
    }

    @DeleteMapping("/{criteriaComparisonId}/hard-delete")
    public ResponseEntity<Void> deleteCriterionComparison(@PathVariable long strategyId, @PathVariable long criteriaComparisonId) {
        
        criterionComparisonService.deleteCriteriaComparison(criteriaComparisonId);

        return ResponseEntity.noContent().build();
    
    }

    // READ
    @GetMapping
    public ResponseEntity<List<CriteriaComparisonReadDTO>> getCriteriaComparisons(
            @PathVariable long strategyId,
            @PathVariable long criteriaGroupId,
            @RequestParam(required = false) Long comparedCriterionId,
            @RequestParam(required = false) Long referenceCriterionId,
            @RequestParam(defaultValue = "false") boolean includeDisabled
    ) {
        
        List<CriteriaComparisonReadDTO> criteriaComparisons = criterionComparisonService.getCriteriaComparisons(
            strategyId, criteriaGroupId, comparedCriterionId, referenceCriterionId, includeDisabled
        );

        return ResponseEntity.ok(criteriaComparisons);
    }

    @GetMapping("/{criteriaComparisonId}")
    public ResponseEntity<CriteriaComparisonReadDTO> getCriteriaComparisonById(
            @PathVariable long strategyId,
            @PathVariable long criteriaComparisonId
    ) {
        
        CriteriaComparisonReadDTO criteriaComparison = criterionComparisonService.getCriteriaComparisonById(criteriaComparisonId);

        return ResponseEntity.ok(criteriaComparison);
    }

}
