package com.pucpr.portplace.features.strategy.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pucpr.portplace.features.strategy.dtos.StrategyCancellationPatchDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyReadDTO;
import com.pucpr.portplace.features.strategy.dtos.StrategyUpdateDTO;
import com.pucpr.portplace.features.strategy.enums.StrategyStatusEnum;
import com.pucpr.portplace.features.strategy.services.StrategyService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Strategy", description = "Related to the Strategy CRUD operations")
@RestController
@RequestMapping("/strategies")
@AllArgsConstructor
public class StrategyController {
    
    private StrategyService strategyService;
    
    //CREATE
    @PostMapping
    ResponseEntity<StrategyReadDTO> createStrategy(
        @RequestBody StrategyCreateDTO dto
    ){

        StrategyReadDTO createdStrategy = strategyService.createStrategy(dto);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdStrategy.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdStrategy);

    }

    //UPDATE
    @PutMapping("/{strategyId}")
    ResponseEntity<StrategyReadDTO> updateStrategy(
        @RequestBody StrategyUpdateDTO dto,
        @PathVariable long strategyId
    ){
        
        StrategyReadDTO updatedStrategy = strategyService.updateStrategy(dto, strategyId);

        return ResponseEntity.ok().body(updatedStrategy);

    }

    @PatchMapping("/{strategyId}/cancel")
    ResponseEntity<StrategyReadDTO> cancelStrategy(
        @RequestBody StrategyCancellationPatchDTO dto,
        @PathVariable long strategyId
    ){
        
        StrategyReadDTO updatedStrategy = strategyService.cancelStrategy(dto, strategyId);

        return ResponseEntity.ok().body(updatedStrategy);

    }

    //DELETE
    @DeleteMapping("/{strategyId}")
    ResponseEntity<StrategyReadDTO> disableStrategy(
        @PathVariable long strategyId
    ){
        
        strategyService.disableStrategy(strategyId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{strategyId}/hard-delete")
    ResponseEntity<StrategyReadDTO> deleteStrategy(
        @PathVariable long strategyId
    ){
        
        strategyService.deleteStrategy(strategyId);

        return ResponseEntity.noContent().build();

    }

    //READ
    @GetMapping
    ResponseEntity<Page<StrategyReadDTO>> getStrategies(
        @RequestParam(required  = false) List<StrategyStatusEnum> status,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ){
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<StrategyReadDTO> strategies = strategyService.getStrategies(status, searchQuery, includeDisabled, pageable);

        return ResponseEntity.ok(strategies);

    }

    @GetMapping("/{strategyId}")
    ResponseEntity<StrategyReadDTO> getStrategy(
        @PathVariable long strategyId
    ){
        
        StrategyReadDTO dto = strategyService.getStrategy(strategyId);

        return ResponseEntity.ok(dto);

    }



}
