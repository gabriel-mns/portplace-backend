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

import com.pucpr.portplace.features.resource.dtos.ResourceCreateDTO;
import com.pucpr.portplace.features.resource.dtos.ResourceReadDTO;
import com.pucpr.portplace.features.resource.dtos.ResourceUpdateDTO;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;
import com.pucpr.portplace.features.resource.services.ResourceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Resources", description = "Related to the Resource operations")
@AllArgsConstructor
@RestController
@RequestMapping("/resources")
public class ResourceController {
    
    private ResourceService resourceService;

    //CREATE
    @PostMapping
    public ResponseEntity<ResourceReadDTO> create(
        @RequestBody @Valid ResourceCreateDTO dto
    ) {

        ResourceReadDTO createdResource = resourceService.create(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdResource.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdResource);

    }

    //UPDATE
    @PutMapping("/{resourceId}")
    public ResponseEntity<ResourceReadDTO> update(
        @PathVariable Long resourceId,
        @RequestBody @Valid ResourceUpdateDTO dto
    ) {
        
        ResourceReadDTO updatedResource = resourceService.update(resourceId, dto);

        return ResponseEntity.ok().body(updatedResource);

    }

    //DELETE
    @DeleteMapping("/{resourceId}")
    public ResponseEntity<Void> disable(
        @PathVariable Long resourceId
    ) {

        resourceService.disable(resourceId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{resourceId}/hard-delete")
    public ResponseEntity<Void> delete(
        @PathVariable Long resourceId
    ) {

        resourceService.delete(resourceId);

        return ResponseEntity.noContent().build();

    }

    //READ
    @GetMapping("/{resourceId}")
    public ResponseEntity<ResourceReadDTO> getById(
        @PathVariable Long resourceId
    ) {

        ResourceReadDTO resource = resourceService.getById(resourceId);

        return ResponseEntity.ok().body(resource);

    }

    @GetMapping
    public ResponseEntity<Page<ResourceReadDTO>> getAll(
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(required = false) List<ResourceStatusEnum> status,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ResourceReadDTO> resources = resourceService.getAll(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok().body(resources);

    }

}
