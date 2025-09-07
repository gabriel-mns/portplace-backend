package com.pucpr.portplace.features.project.controllers;

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

import com.pucpr.portplace.features.project.dtos.EvmEntryCreateDTO;
import com.pucpr.portplace.features.project.dtos.EvmEntryReadDTO;
import com.pucpr.portplace.features.project.dtos.EvmEntryUpdateDTO;
import com.pucpr.portplace.features.project.services.EvmEntryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "EvmEntry", description = "Related to the EVM Entry operations")
@RestController
@RequestMapping("/projects/{projectId}/evm-entries")
@AllArgsConstructor
public class EvmEntryController {
    
    private EvmEntryService service;

    // CREATE
    @PostMapping
    public ResponseEntity<EvmEntryReadDTO> create(
        @PathVariable long projectId, 
        @RequestBody @Valid EvmEntryCreateDTO entry
    ) {

        EvmEntryReadDTO createdEntry = service.createEvmEntry(projectId, entry);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdEntry.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdEntry);

    }
    
    // UPDATE
    @PutMapping("/{entryId}")
    public ResponseEntity<EvmEntryReadDTO> update(
        @PathVariable long entryId, 
        @RequestBody @Valid EvmEntryUpdateDTO entry
    ) {

        EvmEntryReadDTO updatedEntry = service.updateEvmEntry(entryId, entry);

        return ResponseEntity.ok(updatedEntry);

    }

    // DELETE
    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> disableEntry(
        @PathVariable long entryId
    ) {

        service.disableEntry(entryId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{entryId}/hard-delete")
    public ResponseEntity<Void> deleteEntry(
        @PathVariable long entryId
    ) {

        service.deleteEntry(entryId);

        return ResponseEntity.noContent().build();

    }

    // READ
    @GetMapping("/{entryId}")
    public ResponseEntity<EvmEntryReadDTO> getEntry(
        @PathVariable long entryId
    ) {

        EvmEntryReadDTO entry = service.getEvmEntryById(entryId);

        return ResponseEntity.ok(entry);

    }

    @GetMapping
    public ResponseEntity<Page<EvmEntryReadDTO>> getEntries(
        @PathVariable long projectId,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<EvmEntryReadDTO> entries = service.getEvmEntriesByProjectId(projectId, includeDisabled, pageable);

        return ResponseEntity.ok(entries);

    }


}
