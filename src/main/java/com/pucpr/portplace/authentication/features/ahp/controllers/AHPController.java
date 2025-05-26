package com.pucpr.portplace.authentication.features.ahp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.annotation.QueryParam;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.authentication.features.ahp.services.AHPService;
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
        
        return ahpService.getAllAHPs(strategyId, includeDisabled);
    
    }

    @GetMapping("/{AHPId}")
    public ResponseEntity<AHPReadDTO> getAHPById(@PathVariable long strategyId, @PathVariable long AHPId) {
        
        return ahpService.getAHPById(strategyId, AHPId);
    
    }

    @PostMapping
    public ResponseEntity<Void> createAHP(@PathVariable long strategyId, @RequestBody AHPCreateDTO ahpCreateDto) {
        
        return ahpService.createAHP(strategyId, ahpCreateDto);
    
    }

    @PutMapping("/{AHPId}")
    public ResponseEntity<Void> updateAHP(@PathVariable long strategyId,@PathVariable long AHPId, @RequestBody AHPUpdateDTO ahpUpdateDto) {
        
        return ahpService.updateAHP(strategyId, AHPId, ahpUpdateDto);

    }

    @DeleteMapping("/{AHPId}")
    public ResponseEntity<Void> disableAHP(@PathVariable long strategyId, @PathVariable long AHPId) {
        
        return ahpService.disableAHP(strategyId, AHPId);
    
    }

    @DeleteMapping("/{AHPId}/hard-delete")
    public ResponseEntity<Void> deleteAHP(@PathVariable long strategyId, @PathVariable long AHPId) {
        
        return ahpService.deleteAHP(strategyId, AHPId);
    
    }
    

}
