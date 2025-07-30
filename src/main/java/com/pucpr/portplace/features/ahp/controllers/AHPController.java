package com.pucpr.portplace.features.ahp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pucpr.portplace.features.ahp.dtos.AHPCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.AHPReadDTO;
import com.pucpr.portplace.features.ahp.dtos.AHPUpdateDTO;
import com.pucpr.portplace.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.features.ahp.services.AHPService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.net.URI;

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



@Tag(name = "AHP", description = "Related to the AHP CRUD operations")
@RestController
@RequestMapping(StrategyPaths.AHPS)
public class AHPController {
    
    @Autowired
    private AHPService ahpService;


    /*
     * AHP CRUD
     */
    @GetMapping
    public ResponseEntity<Page<AHPReadDTO>> getAllAHPs(
        @PathVariable long strategyId, 
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

        Page<AHPReadDTO> ahps = ahpService.getAllAHPs(strategyId, includeDisabled, pageable);

        return ResponseEntity.ok(ahps);
    
    }

    @GetMapping("/{AHPId}")
    public ResponseEntity<AHPReadDTO> getAHPById(
        @PathVariable long strategyId, 
        @PathVariable long AHPId
        ) {
        
        AHPReadDTO ahp = ahpService.getAHPById(strategyId, AHPId);

        return ResponseEntity.ok(ahp);
    
    }

    @PostMapping
    public ResponseEntity<AHPReadDTO> createAHP(
        @PathVariable long strategyId, 
        @RequestBody @Valid AHPCreateDTO ahpCreateDto
        ) {
        
        AHPReadDTO createdAHP = ahpService.createAHP(strategyId, ahpCreateDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{AHPId}")
            .buildAndExpand(createdAHP.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdAHP);
    
    }

    @PutMapping("/{AHPId}")
    public ResponseEntity<AHPReadDTO> updateAHP(
        @PathVariable long strategyId,
        @PathVariable long AHPId,
        @RequestBody @Valid AHPUpdateDTO ahpUpdateDto
        ) {

        AHPReadDTO updatedAHP = ahpService.updateAHP(strategyId, AHPId, ahpUpdateDto);

        return ResponseEntity.ok().body(updatedAHP);

    }

    @DeleteMapping("/{AHPId}")
    public ResponseEntity<Void> disableAHP(
        @PathVariable long strategyId, 
        @PathVariable long AHPId
        ) {

        ahpService.disableAHP(strategyId, AHPId);

        return ResponseEntity.noContent().build();
    
    }

    @DeleteMapping("/{AHPId}/hard-delete")
    public ResponseEntity<Void> deleteAHP(
        @PathVariable long strategyId, 
        @PathVariable long AHPId
        ) {

        ahpService.deleteAHP(strategyId, AHPId);

        return ResponseEntity.noContent().build();
    
    }
    

}
