package com.pucpr.portplace.features.portfolio.controllers;

import java.net.URI;

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

import com.pucpr.portplace.features.portfolio.dtos.risk.RiskCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.RiskReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.RiskUpdateDTO;
import com.pucpr.portplace.features.portfolio.services.RiskService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Risks", description = "Related to the Risk operations")
@AllArgsConstructor
@RestController
@RequestMapping("/portfolios/{portfolioId}/risks")
public class RiskController {
    
    private RiskService service;

    //CREATE
    @PostMapping
    public ResponseEntity<RiskReadDTO> createRisk(
        @PathVariable Long portfolioId,
        @RequestBody @Valid RiskCreateDTO dto
    ) {

        RiskReadDTO createdRisk = service.createRisk(portfolioId, dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdRisk.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdRisk);

    }

    //UPDATE
    @PutMapping("/{riskId}")
    public ResponseEntity<RiskReadDTO> updateRisk(
        @PathVariable Long riskId,
        @RequestBody @Valid RiskUpdateDTO dto
    ) {

        RiskReadDTO updatedRisk = service.updateRisk(riskId, dto);

        return ResponseEntity.ok(updatedRisk);

    }

    //DELETE
    @DeleteMapping("/{riskId}")
    public ResponseEntity<Void> disableRisk(
        @PathVariable Long riskId
    ) {

        service.disableRisk(riskId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{riskId}/hard-delete")
    public ResponseEntity<Void> deleteRisk(
        @PathVariable Long riskId
    ) {

        service.deleteRisk(riskId);

        return ResponseEntity.noContent().build();

    }


    //READ
    @GetMapping("/{riskId}")
    public ResponseEntity<RiskReadDTO> getById(
        @PathVariable Long riskId
    ) {

        RiskReadDTO risk = service.getById(riskId);

        return ResponseEntity.ok(risk);

    }

    @GetMapping
    public ResponseEntity<Page<RiskReadDTO>> getAllByPortfolioId(
        @PathVariable Long portfolioId,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<RiskReadDTO> risks = service.getAllByPortfolioId(
            portfolioId,
            searchQuery, 
            includeDisabled, 
            pageable
        );

        return ResponseEntity.ok(risks);

    }

}
