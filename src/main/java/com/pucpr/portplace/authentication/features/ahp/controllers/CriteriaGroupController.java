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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.azure.core.annotation.QueryParam;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupListReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.services.CriteriaGroupService;

import jakarta.validation.Valid;

import com.pucpr.portplace.authentication.features.ahp.paths.StrategyPaths;

@RestController
@RequestMapping(StrategyPaths.CRITERIA_GROUPS)
public class CriteriaGroupController {
    
    @Autowired
    private CriteriaGroupService criteriaGroupService;

     // CREATE
    @PostMapping
    public ResponseEntity<CriteriaGroupReadDTO> createCriteriaGroup(@PathVariable long strategyId, @RequestBody @Valid CriteriaGroupCreateDTO criteriaGroupCreateDto) {
        
        CriteriaGroupReadDTO criteriaGroupReadDto = criteriaGroupService.createCriteriaGroup(strategyId, criteriaGroupCreateDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{criteriaGroupId}")
            .buildAndExpand(criteriaGroupReadDto.getId())
            .toUri();

        return ResponseEntity
            .created(location)
            .body(criteriaGroupReadDto);
    
    }

    // UPDATE
    @PutMapping("/{criteriaGroupId}")
    public ResponseEntity<CriteriaGroupReadDTO> updateCriteriaGroup(@PathVariable long strategyId, @PathVariable long criteriaGroupId, @RequestBody @Valid CriteriaGroupUpdateDTO criteriaGroupUpdateDto) {
        
        CriteriaGroupReadDTO criteriaGroupReadDto = criteriaGroupService.updateCriteriaGroup(strategyId, criteriaGroupId, criteriaGroupUpdateDto);

        return ResponseEntity.ok().body(criteriaGroupReadDto);
    
    }

    // DELETE
    @DeleteMapping("/{criteriaGroupId}")
    public ResponseEntity<Void> disableCriteriaGroup(@PathVariable long strategyId, @PathVariable long criteriaGroupId) {

        criteriaGroupService.disableCriteriaGroup(strategyId, criteriaGroupId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{criteriaGroupId}/hard-delete")
    public ResponseEntity<Void> deleteCriteriaGroup(@PathVariable long strategyId, @PathVariable long criteriaGroupId) {
        
        criteriaGroupService.deleteCriteriaGroup(strategyId, criteriaGroupId);
        
        return ResponseEntity.noContent().build(); 

    }
    
    //READ
    @GetMapping
    public ResponseEntity<List<CriteriaGroupListReadDTO>> getCriteriaGroupsByStrategyId(
        @PathVariable long strategyId,
        @QueryParam("includeDisabled") boolean includeDisabled
        ) {
        
        List<CriteriaGroupListReadDTO> criteriaGroupListReadDto = criteriaGroupService.getCriteriaGroupsByStrategyId(strategyId, includeDisabled);

        return ResponseEntity.ok().body(criteriaGroupListReadDto);
    
    }

    @GetMapping("/{criteriaGroupId}")
    public ResponseEntity<CriteriaGroupReadDTO> getCriteriaGroupById(@PathVariable long strategyId, @PathVariable long criteriaGroupId) {
        
        CriteriaGroupReadDTO criteriaGroupReadDto = criteriaGroupService.getCriteriaGroupById(strategyId, criteriaGroupId);

        return ResponseEntity.ok().body(criteriaGroupReadDto);
    
    }

}
