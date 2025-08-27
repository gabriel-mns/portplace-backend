package com.pucpr.portplace.features.strategy.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.pucpr.portplace.features.strategy.dtos.ScenarioCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioReadDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioUpdateDTO;
import com.pucpr.portplace.features.strategy.enums.ScenarioStatusEnum;
import com.pucpr.portplace.features.strategy.services.ScenarioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Scenario", description = "Related to the Scenario CRUD operations")
@RestController
@RequestMapping("/strategies/{strategyId}/scenarios")
@AllArgsConstructor
public class ScenarioController {

    private ScenarioService service;
    //CREATE
    @PostMapping
    public ResponseEntity<ScenarioReadDTO> createScenario(
        @PathVariable long strategyId,
        @RequestBody @Valid ScenarioCreateDTO dto
    ){

        ScenarioReadDTO response = service.createScenario(dto, strategyId);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    //UPDATE
    @PutMapping("/{scenarioId}")
    public ResponseEntity<ScenarioReadDTO> updateScenario(
        @PathVariable long scenarioId,
        @RequestBody @Valid ScenarioUpdateDTO dto
    ){

        ScenarioReadDTO response = service.updateScenario(dto, scenarioId);

        return ResponseEntity.ok().body(response);

    }

    //DELETE
    @DeleteMapping("/{scenarioId}")
    public ResponseEntity<Void> disableScenario(
        @PathVariable long scenarioId
    ){

        service.disableScenario(scenarioId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{scenarioId}/hard-delete")
    public ResponseEntity<Void> deleteScenario(
        @PathVariable long scenarioId
    ){

        service.deleteScenario(scenarioId);

        return ResponseEntity.noContent().build();

    }

    //READ
    @GetMapping
    public ResponseEntity<Page<ScenarioReadDTO>> getAllScenariosByStrategyId(
        @PathVariable long strategyId,
        @RequestParam(defaultValue = "") String name,
        @RequestParam(required = false) ScenarioStatusEnum status,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="10") int size,
        @RequestParam(defaultValue="lastModifiedAt") String sortBy,
        @RequestParam(defaultValue="asc") String sortDir
    ){

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ScenarioReadDTO> response = service.getAllScenarios(strategyId, name, status, pageable, includeDisabled);

        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/{scenarioId}")
    public ResponseEntity<ScenarioReadDTO> getScenarioById(
        @PathVariable long scenarioId
    ){

        ScenarioReadDTO response = service.getScenarioById(scenarioId);

        return ResponseEntity.ok().body(response);

    }

}
