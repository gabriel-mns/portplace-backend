package com.pucpr.portplace.features.resource.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.pucpr.portplace.features.resource.dtos.allocation.AllocationCreateDTO;
import com.pucpr.portplace.features.resource.dtos.allocation.AllocationReadDTO;
import com.pucpr.portplace.features.resource.dtos.allocation.AllocationUpdateDTO;
import com.pucpr.portplace.features.resource.dtos.allocation.DailyAllocationDTO;
import com.pucpr.portplace.features.resource.enums.AllocationStatusEnum;
import com.pucpr.portplace.features.resource.services.AllocationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Allocations", description = "Related to the Allocation operations")
@AllArgsConstructor
@RestController
@RequestMapping("/allocations")
public class AllocationController {
    
    private AllocationService allocationService;

    //CREATE
    @PostMapping
    public ResponseEntity<AllocationReadDTO> create(
        @RequestBody @Valid AllocationCreateDTO dto
    ) {

        AllocationReadDTO allocation = allocationService.create(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(allocation.getId())
            .toUri();

        return ResponseEntity.created(uri).body(allocation);

    }

    //UPDATE
    @PutMapping("/{allocationId}")
    public ResponseEntity<AllocationReadDTO> update(
        @PathVariable Long allocationId,
        @RequestBody @Valid AllocationUpdateDTO dto
    ) {

        AllocationReadDTO allocation = allocationService.update(allocationId, dto);

        return ResponseEntity.ok(allocation);

    }

    //DELETE
    @DeleteMapping("/{allocationId}")
    public ResponseEntity<Void> disable(
        @PathVariable Long allocationId
    ) {
        allocationService.disable(allocationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{allocationId}/hard-delete")
    public ResponseEntity<Void> delete(
        @PathVariable Long allocationId
    ) {
        allocationService.delete(allocationId);
        return ResponseEntity.noContent().build();
    }

    //READ
    @GetMapping("/{allocationId}")
    public ResponseEntity<AllocationReadDTO> findById(
        @PathVariable Long allocationId
    ) {
        AllocationReadDTO allocation = allocationService.getById(allocationId);
        return ResponseEntity.ok(allocation);
    }

    @GetMapping("/analytics")
    public List<DailyAllocationDTO> getAllocationsByDays(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) Long resourceId,
        @RequestParam(required = false) Long projectId
    ) {

        return allocationService.getAllocationsByDateRange(
            startDate, 
            endDate, 
            resourceId, 
            projectId
        );
    }

    @GetMapping
    public ResponseEntity<Page<AllocationReadDTO>> findAll(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) List<AllocationStatusEnum> statuses,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(required = false) Long resourceId,
        @RequestParam(required = false) Long projectId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<AllocationReadDTO> allocations = allocationService.getAll(
            startDate,
            endDate,
            statuses,
            searchQuery,
            includeDisabled,
            resourceId,
            projectId,
            pageable
        );

        return ResponseEntity.ok(allocations);
    }

}
