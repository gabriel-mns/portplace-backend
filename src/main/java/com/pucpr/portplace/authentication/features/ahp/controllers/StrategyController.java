package com.pucpr.portplace.authentication.features.ahp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.annotation.QueryParam;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupListReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.services.CriteriaGroupService;
import com.pucpr.portplace.authentication.features.ahp.services.CriterionComparisonService;
import com.pucpr.portplace.authentication.features.ahp.services.CriterionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/strategies")
public class StrategyController {
    
    @Autowired
    private CriteriaGroupService criteriaGroupService;

    @Autowired
    private CriterionComparisonService criterionComparisonService;

    @Autowired
    private CriterionService criterionService;

    //#region CRITERIA GROUP CRUD

     // CREATE
    @PostMapping("/{strategyId}/criteria-groups")
    public ResponseEntity<Void> createCriteriaGroup(@PathVariable long strategyId, @RequestBody CriteriaGroupCreateDTO criteriaGroupCreateDto) {
        
    return criteriaGroupService.createCriteriaGroup(strategyId, criteriaGroupCreateDto);
    
    }

    // UPDATE
    @PutMapping("/{strategyId}/criteria-groups/{criteriaGroupId}")
    public ResponseEntity<Void> updateCriteriaGroup(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @RequestBody CriteriaGroupUpdateDTO criteriaGroupUpdateDto) {
        
        return criteriaGroupService.updateCriteriaGroup(strategyId, criteriaGroupId, criteriaGroupUpdateDto);
    
    }

    // DELETE
    @DeleteMapping("/{strategyId}/criteria-groups/{criteriaGroupId}")
    public ResponseEntity<Void> disableCriteriaGroup(@PathVariable long strategyId, @PathVariable long criteriaGroupId) {
        
        return criteriaGroupService.disableCriteriaGroup(strategyId, criteriaGroupId);

    }

    @DeleteMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/hard-delete")
    public ResponseEntity<Void> deleteCriteriaGroup(@PathVariable long strategyId, @PathVariable long criteriaGroupId) {
        
        return criteriaGroupService.deleteCriteriaGroup(strategyId, criteriaGroupId);

    }
    
    //READ
    @GetMapping("/{strategyId}/criteria-groups")
    public ResponseEntity<List<CriteriaGroupListReadDTO>> getCriteriaGroupsByStrategyId(@PathVariable long strategyId, @QueryParam("includeDisabled") boolean includeDisabled) {
        
        return criteriaGroupService.getCriteriaGroupsByStrategyId(strategyId, includeDisabled);
    
    }

    @GetMapping("/{strategyId}/criteria-groups/{criteriaGroupId}")
    public ResponseEntity<CriteriaGroupReadDTO> getCriteriaGroupById(@PathVariable long strategyId, @PathVariable long criteriaGroupId) {
        
        return criteriaGroupService.getCriteriaGroupById(strategyId, criteriaGroupId);
    
    }

    //#endregion
    
    //#region CRITERION CRUD
    // READ
    @GetMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria")
    public ResponseEntity<List<CriterionReadDTO>> getAllCriteria(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @RequestParam(defaultValue="false") boolean includeDisabled) {
        
        return criterionService.getCriteriaByCriteriaGroupId(criteriaGroupId, includeDisabled);
    
    }

    @GetMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria/{criterionId}")
    public ResponseEntity<CriterionReadDTO> getCriterionById(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @PathVariable Long criterionId) {
        
        return criterionService.getCriterionById(criteriaGroupId,criterionId);
    
    }

    // CREATE
    @PostMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria")
    public ResponseEntity<Void> createCriterion(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @RequestBody CriterionCreateDTO criterionCreateDTO) {
        
        return criterionService.createCriterion(strategyId, criteriaGroupId, criterionCreateDTO);
    
    }

    // UPDATE
    @PutMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria/{criterionId}")
    public ResponseEntity<Void> updateCriterion(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @PathVariable long criterionId, @RequestBody CriterionUpdateDTO criterionUpdateDTO) {
        
        return criterionService.updateCriterion(criterionId, criterionUpdateDTO);
    
    }

    // DELETE
    @DeleteMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria/{criterionId}")
    public ResponseEntity<Void> disableCriterion(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @PathVariable long criterionId) {
        
        return criterionService.disableCriterion(strategyId, criterionId);
    
    }

    @DeleteMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria/{criterionId}/hard-delete")
    public ResponseEntity<Void> deleteCriterion(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @PathVariable long criterionId) {
        
        return criterionService.deleteCriterion(strategyId, criterionId);
    
    }

    //#endregion

    //#region CRITERION COMPARISON CRUD

    // CREATE
    @PostMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria-comparisons")
    public ResponseEntity<Void> createCriterionComparison(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @RequestBody CriteriaComparisonCreateDTO criteriaComparisonCreateDTO) {
        
        return criterionComparisonService.createCriteriaComparison(strategyId, criteriaGroupId, criteriaComparisonCreateDTO);
    
    }

    // UPDATE
    @PutMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria-comparisons/{criteriaComparisonId}")
    public ResponseEntity<Void> updateCriterionComparison(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @PathVariable long criteriaComparisonId, @RequestBody CriteriaComparisonUpdateDTO criteriaComparisonUpdateDTO) {
        
        return criterionComparisonService.updateCriteriaComparison(criteriaComparisonId, criteriaComparisonUpdateDTO);
    
    }

    // DELETE
    @DeleteMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria-comparisons/{criteriaComparisonId}")
    public ResponseEntity<Void> disableCriterionComparison(@PathVariable long strategyId, @PathVariable long criteriaComparisonId) {
        
        return criterionComparisonService.disableCriteriaComparison(criteriaComparisonId);
    
    }

    @DeleteMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria-comparisons/{criteriaComparsionId}/hard-delete")
    public ResponseEntity<Void> deleteCriterionComparison(@PathVariable long strategyId, @PathVariable long criteriaComparisonId) {
        
        return criterionComparisonService.deleteCriteriaComparison(criteriaComparisonId);
    
    }

    // READ
    @GetMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria-comparisons")
    public ResponseEntity<List<CriteriaComparisonReadDTO>> getCriteriaComparisons(
            @PathVariable long strategyId,
            @PathVariable long criteriaGroupId,
            @RequestParam(required = false) Long comparedCriterionId,
            @RequestParam(required = false) Long referenceCriterionId,
            @RequestParam(defaultValue = "false") boolean includeDisabled
    ) {
        return criterionComparisonService.getCriteriaComparisons(strategyId, criteriaGroupId, comparedCriterionId, referenceCriterionId, includeDisabled);
    }

    @GetMapping("/{strategyId}/criteria-groups/{criteriaGroupId}/criteria-comparisons/{criteriaComparisonId}")
    public ResponseEntity<CriteriaComparisonReadDTO> getCriteriaComparisonById(
            @PathVariable long strategyId,
            @PathVariable long criteriaComparisonId
    ) {
        return criterionComparisonService.getCriteriaComparisonById(criteriaComparisonId);
    }
    
    //#endregion

}
