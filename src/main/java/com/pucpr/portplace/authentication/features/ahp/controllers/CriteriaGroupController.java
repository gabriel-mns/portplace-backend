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
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.annotation.QueryParam;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupListReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.services.CriteriaGroupService;
import com.pucpr.portplace.authentication.features.ahp.paths.StrategyPaths;

@RestController
@RequestMapping(StrategyPaths.CRITERIA_GROUPS)
public class CriteriaGroupController {
    
    @Autowired
    private CriteriaGroupService criteriaGroupService;

     // CREATE
    @PostMapping
    public ResponseEntity<Void> createCriteriaGroup(@PathVariable long strategyId, @RequestBody CriteriaGroupCreateDTO criteriaGroupCreateDto) {
        
        return criteriaGroupService.createCriteriaGroup(strategyId, criteriaGroupCreateDto);
    
    }

    // UPDATE
    @PutMapping("/{criteriaGroupId}")
    public ResponseEntity<Void> updateCriteriaGroup(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @RequestBody CriteriaGroupUpdateDTO criteriaGroupUpdateDto) {
        
        return criteriaGroupService.updateCriteriaGroup(strategyId, criteriaGroupId, criteriaGroupUpdateDto);
    
    }

    // DELETE
    @DeleteMapping("/{criteriaGroupId}")
    public ResponseEntity<Void> disableCriteriaGroup(@PathVariable long strategyId, @PathVariable long criteriaGroupId) {
        
        return criteriaGroupService.disableCriteriaGroup(strategyId, criteriaGroupId);

    }

    @DeleteMapping("/{criteriaGroupId}/hard-delete")
    public ResponseEntity<Void> deleteCriteriaGroup(@PathVariable long strategyId, @PathVariable long criteriaGroupId) {
        
        return criteriaGroupService.deleteCriteriaGroup(strategyId, criteriaGroupId);

    }
    
    //READ
    @GetMapping
    public ResponseEntity<List<CriteriaGroupListReadDTO>> getCriteriaGroupsByStrategyId(@PathVariable long strategyId, @QueryParam("includeDisabled") boolean includeDisabled) {
        
        return criteriaGroupService.getCriteriaGroupsByStrategyId(strategyId, includeDisabled);
    
    }

    @GetMapping("/{criteriaGroupId}")
    public ResponseEntity<CriteriaGroupReadDTO> getCriteriaGroupById(@PathVariable long strategyId, @PathVariable long criteriaGroupId) {
        
        return criteriaGroupService.getCriteriaGroupById(strategyId, criteriaGroupId);
    
    }

}
