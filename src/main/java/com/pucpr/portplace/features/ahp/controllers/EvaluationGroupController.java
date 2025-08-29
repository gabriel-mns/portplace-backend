package com.pucpr.portplace.features.ahp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupReadDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationGroupUpdateDTO;
import com.pucpr.portplace.features.ahp.enums.EvaluationGroupStatusEnum;
import com.pucpr.portplace.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.features.ahp.services.EvaluationGroupService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@Tag(name = "Evaluation Group", description = "Related to the Evaluation Group CRUD operations")
@RestController
@RequestMapping(StrategyPaths.AHPS)
public class EvaluationGroupController {
    
    @Autowired
    private EvaluationGroupService egService;


    /*
     * Evaluation Group CRUD
     */
    @GetMapping
    public ResponseEntity<Page<EvaluationGroupReadDTO>> getAllEvaluationGroups(
        @PathVariable long strategyId,
        @RequestParam(required = false) List<EvaluationGroupStatusEnum> status,
        @RequestParam(defaultValue = "", required = false) String name,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
        ) {
        
        PageRequest pageable = PageRequest.of(
            page,
            size,
            Sort.by(Sort.Direction.fromString(sortDir), sortBy)
        );

        Page<EvaluationGroupReadDTO> egs = egService.getAllEvaluationGroups(
            strategyId,
            status,
            name,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok(egs);
    
    }

    @GetMapping("/{evaluationGroupId}")
    public ResponseEntity<EvaluationGroupReadDTO> getEvaluationGroupById(
        @PathVariable long strategyId, 
        @PathVariable long evaluationGroupId
        ) {
        
        EvaluationGroupReadDTO evaluationGroup = egService.getEvaluationGroupById(strategyId, evaluationGroupId);

        return ResponseEntity.ok(evaluationGroup);
    
    }

    @PostMapping
    public ResponseEntity<EvaluationGroupReadDTO> createEvaluationGroup(
        @PathVariable long strategyId, 
        @RequestBody @Valid EvaluationGroupCreateDTO evaluationGroupCreateDto
        ) {
        
        EvaluationGroupReadDTO createdEvaluationGroup = egService.createEvaluationGroup(strategyId, evaluationGroupCreateDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{evaluationGroupId}")
            .buildAndExpand(createdEvaluationGroup.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdEvaluationGroup);
    
    }

    @PutMapping("/{evaluationGroupId}")
    public ResponseEntity<EvaluationGroupReadDTO> updateAHP(
        @PathVariable long strategyId,
        @PathVariable long evaluationGroupId,
        @RequestBody @Valid EvaluationGroupUpdateDTO evaluationGroupUpdateDto
        ) {

        EvaluationGroupReadDTO updatedEvaluationGroup = egService.updateEvaluationGroup(strategyId, evaluationGroupId, evaluationGroupUpdateDto);

        return ResponseEntity.ok().body(updatedEvaluationGroup);

    }

    @DeleteMapping("/{evaluationGroupId}")
    public ResponseEntity<Void> disableAHP(
        @PathVariable long strategyId, 
        @PathVariable long evaluationGroupId
        ) {

        egService.disableEvaluationGroup(strategyId, evaluationGroupId);

        return ResponseEntity.noContent().build();
    
    }

    @DeleteMapping("/{evaluationGroupId}/hard-delete")
    public ResponseEntity<Void> deleteEvaluationGroup(
        @PathVariable long strategyId, 
        @PathVariable long evaluationGroupId
        ) {

        egService.deleteEvaluationGroup(strategyId, evaluationGroupId);

        return ResponseEntity.noContent().build();
    
    }
    

}
