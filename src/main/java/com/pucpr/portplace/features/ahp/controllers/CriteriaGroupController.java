package com.pucpr.portplace.features.ahp.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupListReadDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupReadDTO;
import com.pucpr.portplace.features.ahp.dtos.CriteriaGroupUpdateDTO;
import com.pucpr.portplace.features.ahp.enums.CriteriaGroupStatusEnum;
import com.pucpr.portplace.features.ahp.services.CriteriaGroupService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.pucpr.portplace.features.ahp.paths.StrategyPaths;

@Tag(name = "Criteria Group", description = "Related to the Criteria Group CRUD operations")
@RestController
@RequestMapping(StrategyPaths.CRITERIA_GROUPS)
@AllArgsConstructor
public class CriteriaGroupController {
    
    private CriteriaGroupService criteriaGroupService;

     // CREATE
    @PostMapping
    public ResponseEntity<CriteriaGroupReadDTO> createCriteriaGroup(
        @PathVariable long strategyId, 
        @RequestBody @Valid CriteriaGroupCreateDTO criteriaGroupCreateDto
     ) {

        CriteriaGroupReadDTO criteriaGroupDto = criteriaGroupService.createCriteriaGroup(strategyId, criteriaGroupCreateDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{criteriaGroupId}")
            .buildAndExpand(criteriaGroupDto.getId())
            .toUri();

        return ResponseEntity
            .created(location)
            .body(criteriaGroupDto);
    
    }

    // UPDATE
    @PutMapping("/{criteriaGroupId}")
    public ResponseEntity<CriteriaGroupReadDTO> updateCriteriaGroup(
        @PathVariable long strategyId, 
        @PathVariable long criteriaGroupId, 
        @RequestBody @Valid CriteriaGroupUpdateDTO criteriaGroupUpdateDto
    ) {
        
        CriteriaGroupReadDTO criteriaGroupDto = criteriaGroupService.updateCriteriaGroup(strategyId, criteriaGroupId, criteriaGroupUpdateDto);

        return ResponseEntity.ok().body(criteriaGroupDto);
    
    }

    // DELETE
    @DeleteMapping("/{criteriaGroupId}")
    public ResponseEntity<Void> disableCriteriaGroup(
        @PathVariable long strategyId, 
        @PathVariable long criteriaGroupId
    ) {

        criteriaGroupService.disableCriteriaGroup(strategyId, criteriaGroupId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{criteriaGroupId}/hard-delete")
    public ResponseEntity<Void> deleteCriteriaGroup(
        @PathVariable long strategyId, 
        @PathVariable long criteriaGroupId
    ) {

        criteriaGroupService.deleteCriteriaGroup(strategyId, criteriaGroupId);
        
        return ResponseEntity.noContent().build(); 

    }
    
    //READ
    @GetMapping
    public ResponseEntity<Page<CriteriaGroupListReadDTO>> getCriteriaGroupsByStrategyId(
        @PathVariable long strategyId,
        @RequestParam(required = false) List<CriteriaGroupStatusEnum> status,
        @RequestParam(defaultValue = "") String name,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<CriteriaGroupListReadDTO> criteriaGroupListReadDto = criteriaGroupService.getCriteriaGroupsByStrategyId(
            strategyId,
            status,
            includeDisabled, 
            name, 
            pageable
        );

        return ResponseEntity.ok().body(criteriaGroupListReadDto);

    }

    @GetMapping("/{criteriaGroupId}")
    public ResponseEntity<CriteriaGroupReadDTO> getCriteriaGroupById(
        @PathVariable long strategyId, 
        @PathVariable long criteriaGroupId
    ) {
        
        CriteriaGroupReadDTO criteriaGroupReadDto = criteriaGroupService.getCriteriaGroupById(strategyId, criteriaGroupId);

        return ResponseEntity.ok().body(criteriaGroupReadDto);
    
    }

}
