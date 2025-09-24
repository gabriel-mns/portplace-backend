package com.pucpr.portplace.features.resource.controllers;

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

import com.pucpr.portplace.features.resource.dtos.AllocationRequestCreateDTO;
import com.pucpr.portplace.features.resource.dtos.AllocationRequestReadDTO;
import com.pucpr.portplace.features.resource.dtos.AllocationRequestUpdateDTO;
import com.pucpr.portplace.features.resource.enums.AllocationRequestStatusEnum;
import com.pucpr.portplace.features.resource.services.AllocationRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Allocation Requests", description = "Related to the AllocationRequest operations")
@AllArgsConstructor
@RestController
@RequestMapping("/allocation-requests")
public class AllocationRequestController {
    
    private AllocationRequestService service;

    //CREATE
    @PostMapping
    public ResponseEntity<AllocationRequestReadDTO> create(
        @RequestBody @Valid AllocationRequestCreateDTO dto
    ) {

        AllocationRequestReadDTO createdAllocationRequest = service.create(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdAllocationRequest.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdAllocationRequest);

    }

    //UPDATE
    @PutMapping("/{allocationRequestId}")
    public ResponseEntity<AllocationRequestReadDTO> update(
        @PathVariable Long allocationRequestId,
        @RequestBody @Valid AllocationRequestUpdateDTO dto
    ) {
        
        AllocationRequestReadDTO updatedAllocationRequest = service.update(allocationRequestId, dto);

        return ResponseEntity.ok().body(updatedAllocationRequest);

    }

    //DELETE
    @DeleteMapping("/{allocationRequestId}")
    public ResponseEntity<Void> disable(
        @PathVariable Long allocationRequestId
    ) {

        service.disable(allocationRequestId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{allocationRequestId}/hard-delete")
    public ResponseEntity<Void> delete(
        @PathVariable Long allocationRequestId
    ) {

        service.delete(allocationRequestId);

        return ResponseEntity.noContent().build();

    }

    //READ
    @GetMapping("/{allocationRequestId}")
    public ResponseEntity<AllocationRequestReadDTO> getById(
        @PathVariable Long allocationRequestId
    ) {

        AllocationRequestReadDTO allocationRequest = service.findById(allocationRequestId);

        return ResponseEntity.ok().body(allocationRequest);

    }

    @GetMapping
    public ResponseEntity<Page<AllocationRequestReadDTO>> getAll(
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(required = false) List<AllocationRequestStatusEnum> status,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<AllocationRequestReadDTO> allocationRequests = service.findAll(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok().body(allocationRequests);

    }

}
