package com.pucpr.portplace.features.ahp.controllers;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public ResponseEntity<Page<EvaluationReadDTO>> getAllEvaluationsByEvaluationGroupId(
        @PathVariable long evaluationGroupId, 
        @RequestParam(required = false) String name,
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

        Page<EvaluationReadDTO> evaluations = evaluationService.getAllEvaluationsByEvaluationGroupId(
            evaluationGroupId, name, includeDisabled, pageable
        );

        return ResponseEntity.ok().body(evaluations);
        
    }

    @GetMapping("/{evaluationId}")
    public ResponseEntity<EvaluationReadDTO> getEvaluationById(@PathVariable long evaluationGroupId, @PathVariable long evaluationId) {
        
        EvaluationReadDTO evaluationReadDTO = evaluationService.getEvaluationById(evaluationGroupId, evaluationId);

        return ResponseEntity.ok().body(evaluationReadDTO);
        
    }

    @PostMapping
    public ResponseEntity<EvaluationReadDTO> createEvaluation(@PathVariable long evaluationGroupId, @RequestBody @Valid EvaluationCreateDTO evaluationCreateDTO) {

        EvaluationReadDTO evaluationReadDTO = evaluationService.createEvaluation(evaluationGroupId, evaluationCreateDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{evaluationId}")
                .buildAndExpand(evaluationReadDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(evaluationReadDTO);
        
    }

    @PutMapping("/{evaluationId}")
    public ResponseEntity<EvaluationReadDTO> updateEvaluation(@PathVariable long evaluationGroupId, @PathVariable long evaluationId, @RequestBody @Valid EvaluationUpdateDTO evaluationUpdateDTO) {

        EvaluationReadDTO evaluationReadDTO = evaluationService.updateEvaluation(evaluationGroupId, evaluationId, evaluationUpdateDTO);

        return ResponseEntity.ok().body(evaluationReadDTO);
    }

    @DeleteMapping("/{evaluationId}")
    public ResponseEntity<Void> disableEvaluation(@PathVariable long evaluationGroupId, @PathVariable long evaluationId) {
        
        evaluationService.disableEvaluation(evaluationGroupId, evaluationId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{evaluationId}/hard-delete")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable long evaluationGroupId, @PathVariable long evaluationId) {
        
        evaluationService.deleteEvaluation(evaluationGroupId, evaluationId);

        return ResponseEntity.noContent().build();
        
    }

}
