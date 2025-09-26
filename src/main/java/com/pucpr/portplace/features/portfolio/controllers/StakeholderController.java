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

import com.pucpr.portplace.features.portfolio.dtos.event.EventReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.stakeholder.StakeholderCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.stakeholder.StakeholderReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.stakeholder.StakeholderUpdateDTO;
import com.pucpr.portplace.features.portfolio.services.StakeholderService;
import com.pucpr.portplace.features.portfolio.services.internal.EventEntityService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Portfolio", description = "Related to the Portfolio operations")
@AllArgsConstructor
@RestController
@RequestMapping("/portfolios/{portfolioId}/stakeholders")
public class StakeholderController {

    private StakeholderService service;
    private EventEntityService eventEntityService;

    // CREATE
    @PostMapping
    private ResponseEntity<StakeholderReadDTO> createStakeholder(
        @PathVariable Long portfolioId,
        @RequestBody @Valid StakeholderCreateDTO stakeholderCreateDTO
    ) {

        StakeholderReadDTO createdStakeholder = service.createStakeholder(portfolioId, stakeholderCreateDTO);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdStakeholder.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdStakeholder);

    }
    
    // UPDATE
    @PutMapping("/{stakeholderId}")
    private ResponseEntity<StakeholderReadDTO> updateStakeholder(
        @PathVariable Long stakeholderId,
        @RequestBody @Valid StakeholderUpdateDTO dto
    ) {

        StakeholderReadDTO updatedStakeholder = service.updateStakeholder(stakeholderId, dto);

        return ResponseEntity.ok(updatedStakeholder);

    }
    
    // DELETE
    @DeleteMapping("/{stakeholderId}")
    private ResponseEntity<Void> disableStakeholder(
        @PathVariable Long stakeholderId
    ){

        service.disableStakeholder(stakeholderId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{stakeholderId}/hard-delete")
    private ResponseEntity<Void> deleteStakeholder(
        @PathVariable Long stakeholderId
    ){

        service.deleteStakeholder(stakeholderId);

        return ResponseEntity.noContent().build();

    }

    
    // READ
    @GetMapping
    private ResponseEntity<Page<StakeholderReadDTO>> getStakeholders(
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

        Page<StakeholderReadDTO> stakeholders = service.getStakeholders(
            portfolioId,
            searchQuery,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok(stakeholders);

    }
    
    @GetMapping("/{stakeholderId}")
    private ResponseEntity<StakeholderReadDTO> getStakeholderById(
        @PathVariable Long stakeholderId
    ) {
        
        StakeholderReadDTO stakeholder = service.getStakeholderById(stakeholderId);
    
        return ResponseEntity.ok(stakeholder);

    }

    
    @GetMapping("/available-for-event/{eventId}")
    private ResponseEntity<List<StakeholderReadDTO>> getStakeholdersAvailableForEvent(
        @PathVariable Long portfolioId,
        @PathVariable Long eventId,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled
    ) {

        List<StakeholderReadDTO> stakeholders = service.getStakeholdersAvailableForEvent(
            portfolioId,
            eventId,
            searchQuery,
            includeDisabled
        );

        return ResponseEntity.ok(stakeholders);

    }

    
    @GetMapping("/{stakeholderId}/events")
    private ResponseEntity<Page<EventReadDTO>> getEventsByStakeholder(
        @PathVariable Long stakeholderId,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<EventReadDTO> events = eventEntityService.findByStakeholderId(
            stakeholderId,
            searchQuery,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok(events);

    }
    
}
