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

import com.pucpr.portplace.features.resource.dtos.position.PositionCreateDTO;
import com.pucpr.portplace.features.resource.dtos.position.PositionReadDTO;
import com.pucpr.portplace.features.resource.dtos.position.PositionUpdateDTO;
import com.pucpr.portplace.features.resource.enums.ResourceStatusEnum;
import com.pucpr.portplace.features.resource.services.PositionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Positions", description = "Related to the Position operations")
@AllArgsConstructor
@RestController
@RequestMapping("/positions")
public class PositionController {
    
    private final PositionService positionService;

    //CREATE
    @PostMapping
    public ResponseEntity<PositionReadDTO> createPosition(
        @RequestBody @Valid PositionCreateDTO positionCreateDTO
    ) {

        PositionReadDTO newPosition = positionService.createPosition(positionCreateDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newPosition.getId())
            .toUri();

        return ResponseEntity.created(uri).body(newPosition);

    }

    //UPDATE
    @PutMapping("/{positionId}")
    public ResponseEntity<PositionReadDTO> updatePosition(
        @PathVariable Long positionId,
        @RequestBody @Valid PositionUpdateDTO dto
    ) {

        PositionReadDTO updatedPosition = positionService.updatePosition(positionId, dto);

        return ResponseEntity.ok().body(updatedPosition);

    }

    //DELETE
    @DeleteMapping("/{positionId}")
    public ResponseEntity<Void> disablePosition(
        @PathVariable Long positionId
    ) {

        positionService.disablePosition(positionId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{positionId}/hard-delete")
    public ResponseEntity<Void> deletePosition(
        @PathVariable Long positionId
    ) {

        positionService.deletePosition(positionId);

        return ResponseEntity.noContent().build();

    }

    //READ
    @GetMapping("/{positionId}")
    public ResponseEntity<PositionReadDTO> getPositionById(
        @PathVariable Long positionId
    ) {

        PositionReadDTO position = positionService.getPositionById(positionId);

        return ResponseEntity.ok().body(position);

    }

    @GetMapping
    public ResponseEntity<Page<PositionReadDTO>> getAllPositions(
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

        Page<PositionReadDTO> positions = positionService.getAllPositions(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok().body(positions);

    }

    @GetMapping("/unpaged")
    public ResponseEntity<List<PositionReadDTO>> getAllPositionsUnpaged(
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(required = false) List<ResourceStatusEnum> status,
        @RequestParam(defaultValue = "false") boolean includeDisabled
    ) {

        List<PositionReadDTO> positions = positionService.getAllPositionsUnpaged(
            status,
            searchQuery,
            includeDisabled
        );

        return ResponseEntity.ok().body(positions);

    }

}
