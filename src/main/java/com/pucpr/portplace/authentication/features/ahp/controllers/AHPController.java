package com.pucpr.portplace.authentication.features.ahp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.azure.core.annotation.QueryParam;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.authentication.features.ahp.services.AHPService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping(StrategyPaths.AHPS)
public class AHPController {
    
    @Autowired
    private AHPService ahpService;


    /*
     * AHP CRUD
     */
    @GetMapping
    public ResponseEntity<List<AHPReadDTO>> getAllAHPs(@PathVariable long strategyId, @QueryParam("includeDisabled") boolean includeDisabled) {
        
        List<AHPReadDTO> ahps = ahpService.getAllAHPs(strategyId, includeDisabled);

        return ResponseEntity.ok(ahps);
    
    }

    @GetMapping("/{AHPId}")
    public ResponseEntity<AHPReadDTO> getAHPById(@PathVariable long strategyId, @PathVariable long AHPId) {
        
        AHPReadDTO ahp = ahpService.getAHPById(strategyId, AHPId);

        return ResponseEntity.ok(ahp);
    
    }

    @PostMapping
    public ResponseEntity<AHPReadDTO> createAHP(@PathVariable long strategyId, @RequestBody @Valid AHPCreateDTO ahpCreateDto) {
        
        AHPReadDTO createdAHP = ahpService.createAHP(strategyId, ahpCreateDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{AHPId}")
            .buildAndExpand(createdAHP.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdAHP);
    
    }

    @PutMapping("/{AHPId}")
    public ResponseEntity<AHPReadDTO> updateAHP(@PathVariable long strategyId,@PathVariable long AHPId, @RequestBody @Valid AHPUpdateDTO ahpUpdateDto) {
        
        AHPReadDTO updatedAHP = ahpService.updateAHP(strategyId, AHPId, ahpUpdateDto);

        return ResponseEntity.ok().body(updatedAHP);

    }

    @DeleteMapping("/{AHPId}")
    public ResponseEntity<Void> disableAHP(@PathVariable long strategyId, @PathVariable long AHPId) {
        
        ahpService.disableAHP(strategyId, AHPId);

        return ResponseEntity.noContent().build();
    
    }

    @DeleteMapping("/{AHPId}/hard-delete")
    public ResponseEntity<Void> deleteAHP(@PathVariable long strategyId, @PathVariable long AHPId) {
        
        ahpService.deleteAHP(strategyId, AHPId);

        return ResponseEntity.noContent().build();
    
    }
    

}
