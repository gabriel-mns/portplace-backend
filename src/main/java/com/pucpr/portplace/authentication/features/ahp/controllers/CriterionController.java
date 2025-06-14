package com.pucpr.portplace.authentication.features.ahp.controllers;

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

import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.authentication.features.ahp.services.CriterionService;

@RestController
@RequestMapping(StrategyPaths.CRITERIA)
public class CriterionController {
    
    
    @Autowired
    private CriterionService criterionService;

    
    //#region CRITERION CRUD
    // READ
    @GetMapping
    public ResponseEntity<List<CriterionReadDTO>> getAllCriteria(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @RequestParam(defaultValue="false") boolean includeDisabled) {

        return criterionService.getCriteriaByCriteriaGroupId(criteriaGroupId, includeDisabled);
    
    }

    @GetMapping("/{criterionId}")
    public ResponseEntity<CriterionReadDTO> getCriterionById(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @PathVariable Long criterionId) {
        
        return criterionService.getCriterionById(criteriaGroupId,criterionId);
    
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Void> createCriterion(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @RequestBody CriterionCreateDTO criterionCreateDTO) {
        
        return criterionService.createCriterion(strategyId, criteriaGroupId, criterionCreateDTO);
    
    }

    // UPDATE
    @PutMapping("/{criterionId}")
    public ResponseEntity<Void> updateCriterion(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @PathVariable long criterionId, @RequestBody CriterionUpdateDTO criterionUpdateDTO) {
        
        return criterionService.updateCriterion(criterionId, criterionUpdateDTO);
    
    }

    // DELETE
    @DeleteMapping("/{criterionId}")
    public ResponseEntity<Void> disableCriterion(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @PathVariable long criterionId) {
        
        return criterionService.disableCriterion(strategyId, criterionId);
    
    }

    @DeleteMapping("/{criterionId}/hard-delete")
    public ResponseEntity<Void> deleteCriterion(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @PathVariable long criterionId) {
        
        return criterionService.deleteCriterion(strategyId, criterionId);
    
    }

}
