package com.pucpr.portplace.features.ahp.controllers;

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
import com.pucpr.portplace.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationReadDTO;
import com.pucpr.portplace.features.ahp.dtos.EvaluationUpdateDTO;
import com.pucpr.portplace.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.features.ahp.services.EvaluationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Evaluation", description = "Related to the Evaluation CRUD operations")
@RestController
@RequestMapping(StrategyPaths.EVALUATIONS)
public class EvaluationController {
    
    @Autowired
    private EvaluationService evaluationService;

    @GetMapping
    public ResponseEntity<List<EvaluationReadDTO>> getAllEvaluationsByAHPId(@PathVariable long ahpId, @QueryParam("includeDisabled") boolean includeDisabled) {
        
        List<EvaluationReadDTO> evaluations = evaluationService.getAllEvaluationsByAHPId(ahpId, includeDisabled);

        return ResponseEntity.ok().body(evaluations);
        
    }

    @GetMapping("/{evaluationId}")
    public ResponseEntity<EvaluationReadDTO> getEvaluationById(@PathVariable long ahpId, @PathVariable long evaluationId) {
        
        EvaluationReadDTO evaluationReadDTO = evaluationService.getEvaluationById(ahpId, evaluationId);

        return ResponseEntity.ok().body(evaluationReadDTO);
        
    }

    @PostMapping
    public ResponseEntity<EvaluationReadDTO> createEvaluation(@PathVariable long ahpId, @RequestBody @Valid EvaluationCreateDTO evaluationCreateDTO) {

        EvaluationReadDTO evaluationReadDTO = evaluationService.createEvaluation(ahpId, evaluationCreateDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{evaluationId}")
                .buildAndExpand(evaluationReadDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(evaluationReadDTO);
        
    }

    @PutMapping("/{evaluationId}")
    public ResponseEntity<EvaluationReadDTO> updateEvaluation(@PathVariable long ahpId, @PathVariable long evaluationId, @RequestBody @Valid EvaluationUpdateDTO evaluationUpdateDTO) {

        EvaluationReadDTO evaluationReadDTO = evaluationService.updateEvaluation(ahpId, evaluationId, evaluationUpdateDTO);

        return ResponseEntity.ok().body(evaluationReadDTO);
    }

    @DeleteMapping("/{evaluationId}")
    public ResponseEntity<Void> disableEvaluation(@PathVariable long ahpId, @PathVariable long evaluationId) {
        
        evaluationService.disableEvaluation(ahpId, evaluationId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{evaluationId}/hard-delete")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable long ahpId, @PathVariable long evaluationId) {
        
        evaluationService.deleteEvaluation(ahpId, evaluationId);

        return ResponseEntity.noContent().build();
        
    }

}
