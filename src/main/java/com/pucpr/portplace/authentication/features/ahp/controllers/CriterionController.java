package com.pucpr.portplace.authentication.features.ahp.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    
    //#region READ
    @GetMapping
    public ResponseEntity<List<CriterionReadDTO>> getAllCriteria(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @RequestParam(defaultValue="false") boolean includeDisabled) {

        List<CriterionReadDTO> criteria = criterionService.getCriteriaByCriteriaGroupId(criteriaGroupId, includeDisabled);

        return ResponseEntity.ok(criteria);
    
    }

    @GetMapping("/{criterionId}")
    public ResponseEntity<CriterionReadDTO> getCriterionById(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @PathVariable Long criterionId) {
        
        CriterionReadDTO criterion = criterionService.getCriterionById(criteriaGroupId, criterionId);

        return ResponseEntity.ok(criterion);
    
    }

    //#region CREATE
    @PostMapping
    public ResponseEntity<CriterionReadDTO> createCriterion(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @RequestBody CriterionCreateDTO criterionCreateDTO) {
        
        CriterionReadDTO createdCriterion = criterionService.createCriterion(strategyId, criteriaGroupId, criterionCreateDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{criterionId}")
                .buildAndExpand(createdCriterion.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdCriterion);
    
    }

    //#region UPDATE
    @PutMapping("/{criterionId}")
    public ResponseEntity<CriterionReadDTO> updateCriterion(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @PathVariable long criterionId, @RequestBody CriterionUpdateDTO criterionUpdateDTO) {
        
        CriterionReadDTO updatedCriterion = criterionService.updateCriterion(criterionId, criterionUpdateDTO);

        return ResponseEntity.ok().body(updatedCriterion);
    
    }

    //#region DELETE
    @DeleteMapping("/{criterionId}")
    public ResponseEntity<Void> disableCriterion(@PathVariable long strategyId, @PathVariable Long criteriaGroupId, @PathVariable long criterionId) {
        
        criterionService.disableCriterion(strategyId, criterionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    
    }

    @DeleteMapping("/{criterionId}/hard-delete")
    public ResponseEntity<Void> deleteCriterion(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @PathVariable long criterionId) {
        
        criterionService.deleteCriterion(strategyId, criterionId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    
    }

}
