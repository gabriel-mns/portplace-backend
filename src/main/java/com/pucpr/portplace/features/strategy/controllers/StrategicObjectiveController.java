package com.pucpr.portplace.features.strategy.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pucpr.portplace.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.features.ahp.services.internal.CriterionEntityService;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveUpdateDTO;
import com.pucpr.portplace.features.strategy.enums.StrategicObjectiveStatusEnum;
import com.pucpr.portplace.features.strategy.services.StrategicObjectiveService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "StrategicObjective", description = "Related to the StrategicObjective CRUD operations")
@RestController
@RequestMapping("/strategies/{strategyId}/strategic-objectives")
@AllArgsConstructor
public class StrategicObjectiveController {
    
    private StrategicObjectiveService service;
    private CriterionEntityService criterionService;

    //CREATE
    @PostMapping
    public ResponseEntity<StrategicObjectiveReadDTO> createObjective(
        @PathVariable long strategyId,
        @RequestBody @Valid StrategicObjectiveCreateDTO dto
    ){

        StrategicObjectiveReadDTO response = service.createStrategicObjective(dto, strategyId);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    //UPDATE
    @PutMapping("/{objectiveId}")
    public ResponseEntity<StrategicObjectiveReadDTO> updateObjective(
        @PathVariable long objectiveId,
        @RequestBody @Valid StrategicObjectiveUpdateDTO dto
    ){

        StrategicObjectiveReadDTO response = service.updateStrategicObjective(objectiveId, dto);

        return ResponseEntity.ok(response);

    }

    //DELETE
    @DeleteMapping("/{objectiveId}")
    public ResponseEntity<Void> disableObjective(
        @PathVariable long objectiveId
    ){

        service.disableStrategicObjective(objectiveId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{objectiveId}/hard-delete")
    public ResponseEntity<Void> deleteObjective(
        @PathVariable long objectiveId
    ){

        service.deleteStrategicObjective(objectiveId);

        return ResponseEntity.noContent().build();

    }

    //READ
    @GetMapping
    public ResponseEntity<Page<StrategicObjectiveReadDTO>> getObjectives(
        @PathVariable long strategyId,
        @RequestParam(required = false) List<StrategicObjectiveStatusEnum> status,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="10") int size,
        @RequestParam(defaultValue="lastModifiedAt") String sortBy,
        @RequestParam(defaultValue="asc") String sortDir
    ) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<StrategicObjectiveReadDTO> response = service.getStrategicObjectives(
            strategyId,
            status,
            includeDisabled,
            searchQuery,
            pageable
        );

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{objectiveId}")
    public ResponseEntity<StrategicObjectiveReadDTO> getObjective(
        @PathVariable long objectiveId
    ) {
        
        StrategicObjectiveReadDTO response = service.getStrategicObjective(objectiveId);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{objectiveId}/criteria")
    public ResponseEntity<Page<CriterionReadDTO>> getObjectiveCriteria(
        @PathVariable long objectiveId,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<CriterionReadDTO> response = criterionService.findByStrategicObjectiveId(
            objectiveId,
            searchQuery,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok(response);

    }

}
