package com.pucpr.portplace.authentication.features.ahp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.annotation.QueryParam;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPReadDTO;
import com.pucpr.portplace.authentication.features.ahp.services.AHPService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/ahp")
public class AHPController {
    
    @Autowired
    private AHPService ahpService;


    /*
     * AHP CRUD
     */
    @GetMapping
    public ResponseEntity<List<AHPReadDTO>> getAllAHPs(@QueryParam("includeDisabled") boolean includeDisabled) {
        
        return ahpService.getAllAHPs(includeDisabled);
    
    }

    @GetMapping("/{AHPId}")
    public ResponseEntity<AHPReadDTO> getAHPById(@PathVariable long AHPId) {
        
        return ahpService.getAHPById(AHPId);
    
    }

    @PostMapping
    public ResponseEntity<Void> createAHP() {
        
        return ahpService.createAHP();
    
    }

    @DeleteMapping("/{AHPId}")
    public ResponseEntity<Void> disableAHP(@PathVariable long AHPId) {
        
        return ahpService.disableAHP(AHPId);
    
    }

    @DeleteMapping("/{AHPId}/hard-delete")
    public ResponseEntity<Void> deleteAHP(@PathVariable long AHPId) {
        
        return ahpService.deleteAHP(AHPId);
    
    }
    

}
