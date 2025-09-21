package com.pucpr.portplace.features.portfolio.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurenceCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurrenceReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.occurence.RiskOccurrenceUpdateDTO;
import com.pucpr.portplace.features.portfolio.enums.RiskOccurrenceStatusEnum;
import com.pucpr.portplace.features.portfolio.services.RiskOccurenceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Risk Occurences", description = "Related to the Risk Occurence operations")
@AllArgsConstructor
@RestController
@RequestMapping("/portfolios/{portfolioId}/risks/{riskId}/occurrences")
public class RiskOccurrrenceController {
    
    private RiskOccurenceService service;

    //CREATE
    @PostMapping
    public ResponseEntity<RiskOccurrenceReadDTO> createRiskOccurrence(
        @PathVariable Long riskId, 
        @RequestBody @Valid RiskOccurenceCreateDTO dto
    ) {
        RiskOccurrenceReadDTO createdOccurrence = service.create(riskId, dto);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdOccurrence.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdOccurrence);

    }

    //UPDATE
    @PutMapping("/{occurrenceId}")
    public ResponseEntity<RiskOccurrenceReadDTO> updateRiskOccurrence(
        @PathVariable Long occurrenceId,
        @RequestBody @Valid RiskOccurrenceUpdateDTO dto
    ) {

        RiskOccurrenceReadDTO updatedOccurrence = service.update(occurrenceId, dto);
        
        return ResponseEntity.ok(updatedOccurrence);

    }

    //DELETE
    @DeleteMapping("/{occurrenceId}")
    public ResponseEntity<Void> disableRiskOccurrence(
        @PathVariable Long occurrenceId
    ) {
        service.disable(occurrenceId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{occurrenceId}/hard-delete")
    public ResponseEntity<Void> deleteRiskOccurrence(
        @PathVariable Long occurrenceId
    ) {
        service.delete(occurrenceId);
        return ResponseEntity.noContent().build();
    }

    //READ
    @GetMapping("/{occurrenceId}")
    public ResponseEntity<RiskOccurrenceReadDTO> getRiskOccurrenceById(
        @PathVariable Long occurrenceId
    ) {

        RiskOccurrenceReadDTO occurrence = service.findById(occurrenceId);

        return ResponseEntity.ok(occurrence);

    }

    @GetMapping
    public ResponseEntity<Page<RiskOccurrenceReadDTO>> getRiskOccurrencesByFilters(
        @PathVariable Long riskId,
        @RequestParam(required = false) List<RiskOccurrenceStatusEnum> status,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

                        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<RiskOccurrenceReadDTO> occurrences = service.findAll(
            riskId,
            status,
            includeDisabled,
            searchQuery,
            pageable
        );

        return ResponseEntity.ok(occurrences);

    }

}
