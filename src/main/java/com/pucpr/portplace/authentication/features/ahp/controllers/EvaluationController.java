package com.pucpr.portplace.authentication.features.ahp.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.azure.core.annotation.QueryParam;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationReadDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationUpdateDTO;
import com.pucpr.portplace.authentication.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.authentication.features.ahp.services.EvaluationService;

@RestController
@RequestMapping(StrategyPaths.EVALUATIONS)
public class EvaluationController {
    
    @Autowired
    private EvaluationService evaluationService;

    @GetMapping
    public ResponseEntity<List<EvaluationReadDTO>> getAllEvaluationsByAHPId(@PathVariable long ahpId, @QueryParam("includeDisabled") boolean includeDisabled) {
        
        return evaluationService.getAllEvaluationsByAHPId(ahpId, includeDisabled);
        
    }

    @GetMapping("/{evaluationId}")
    public ResponseEntity<EvaluationReadDTO> getEvaluationById(@PathVariable long ahpId, @PathVariable long evaluationId) {
        
        return evaluationService.getEvaluationById(ahpId, evaluationId);
        
    }

    @PostMapping
    public ResponseEntity<EvaluationReadDTO> createEvaluation(@PathVariable long ahpId, @RequestBody EvaluationCreateDTO evaluationCreateDTO) {
        
        EvaluationReadDTO evaluationReadDTO = evaluationService.createEvaluation(ahpId, evaluationCreateDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{evaluationId}")
                .buildAndExpand(evaluationReadDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(evaluationReadDTO);
        
    }

    @PutMapping("/{evaluationId}")
    public ResponseEntity<Void> updateEvaluation(@PathVariable long ahpId, @PathVariable long evaluationId, @RequestBody EvaluationUpdateDTO evaluationUpdateDTO) {
        
        return evaluationService.updateEvaluation(ahpId, evaluationId, evaluationUpdateDTO);
        
    }

    @DeleteMapping("/{evaluationId}")
    public ResponseEntity<Void> disableEvaluation(@PathVariable long ahpId, @PathVariable long evaluationId) {
        
        return evaluationService.disableEvaluation(ahpId, evaluationId);
        
    }

    @DeleteMapping("/{evaluationId}/hard-delete")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable long ahpId, @PathVariable long evaluationId) {
        
        return evaluationService.deleteEvaluation(ahpId, evaluationId);
        
    }

}
