package com.pucpr.portplace.authentication.features.ahp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.annotation.QueryParam;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPReadDto;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.services.AHPService;
import com.pucpr.portplace.authentication.features.ahp.services.CriterionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/ahp")
public class AHPController {
    
    @Autowired
    private AHPService ahpService;

    @Autowired
    private CriterionService criterionService;

    /*
     * AHP CRUD
     */
    @GetMapping
    public ResponseEntity<List<AHPReadDto>> getAllAHPs(@QueryParam("includeDisabled") boolean includeDisabled) {
        
        return ahpService.getAllAHPs(includeDisabled);
    
    }

    @GetMapping("/{AHPId}")
    public ResponseEntity<AHPReadDto> getAHPById(@PathVariable Long AHPId) {
        
        return ahpService.getAHPById(AHPId);
    
    }

    @PostMapping
    public ResponseEntity<Void> createAHP() {
        
        return ahpService.createAHP();
    
    }

    @DeleteMapping("/{AHPId}")
    public ResponseEntity<Void> disableAHP(@PathVariable Long AHPId) {
        
        return ahpService.disableAHP(AHPId);
    
    }

    @DeleteMapping("/{AHPId}/hard-delete")
    public ResponseEntity<Void> deleteAHP(@PathVariable Long AHPId) {
        
        return ahpService.deleteAHP(AHPId);
    
    }
    

    /*
     * CRITERION CRUD
     */
    @GetMapping("/{AHPId}/criteria")
    public ResponseEntity<List<CriterionReadDTO>> getAllCriteria(@PathVariable Long AHPId, @RequestParam(defaultValue="false") boolean includeDisabled) {
        
        return criterionService.getCriteriaByAHPId(AHPId, includeDisabled);
    
    }

    @GetMapping("/{AHPId}/criteria/{criterionId}")
    public ResponseEntity<CriterionReadDTO> getAllCriteria(@PathVariable Long AHPId, @PathVariable Long criterionId) {
        
        return criterionService.getCriterionById(criterionId);
    
    }

    @PostMapping("/{AHPId}/criteria")
    public ResponseEntity<Void> createCriterion(@PathVariable Long AHPId, @RequestBody CriterionCreateDTO criterionCreateDTO) {
        
        return criterionService.createCriterion(AHPId, criterionCreateDTO);
    
    }

    @PutMapping("/{AHPId}/criteria/{criterionId}")
    public ResponseEntity<Void> updateCriterion(@PathVariable Long AHPId, @PathVariable long criterionId, @RequestBody CriterionUpdateDTO criterionUpdateDTO) {
        
        return criterionService.updateCriterion(criterionId, criterionUpdateDTO);
    
    }

    @DeleteMapping("/{AHPId}/criteria/{criterionId}")
    public ResponseEntity<Void> disableCriterion(@PathVariable Long AHPId, @PathVariable long criterionId) {
        
        return criterionService.disableCriterion(criterionId);
    
    }

    @DeleteMapping("/{AHPId}/criteria/{criterionId}/hard-delete")
    public ResponseEntity<Void> deleteCriterion(@PathVariable Long AHPId, @PathVariable long criterionId) {
        
        return criterionService.deleteCriterion(criterionId);
    
    }

}
