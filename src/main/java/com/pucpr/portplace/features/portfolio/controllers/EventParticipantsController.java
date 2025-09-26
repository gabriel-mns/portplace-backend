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

import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventParticipantUpdateDTO;
import com.pucpr.portplace.features.portfolio.services.EventParticipantService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Events", description = "Related to the Event operations")
@AllArgsConstructor
@RestController
@RequestMapping("/portfolios/{portfolioId}/events/{eventId}/participants")
public class EventParticipantsController {
    
    private EventParticipantService service;

    //CREATE
    @PostMapping
    public ResponseEntity<EventParticipantReadDTO> addParticipant(
        @PathVariable Long eventId,
        @RequestBody @Valid EventParticipantCreateDTO dto
    ) {

        EventParticipantReadDTO created = service.addParticipant(eventId, dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();

        return ResponseEntity.created(uri).body(created);

    }

    //UPDATE
    @PutMapping("/{participantId}")
    public ResponseEntity<EventParticipantReadDTO> updateParticipant(
        @PathVariable Long participantId,
        @RequestBody @Valid EventParticipantUpdateDTO dto
    ) {

        EventParticipantReadDTO updated = service.updateParticipant(participantId, dto);

        return ResponseEntity.ok(updated);

    }

    //DELETE
    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> disableParticipant(
        @PathVariable Long participantId
    ) {

        service.disableParticipant(participantId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{participantId}/hard-delete")
    public ResponseEntity<Void> deleteParticipant(
        @PathVariable Long participantId
    ) {

        service.deleteParticipant(participantId);

        return ResponseEntity.noContent().build();

    }
    
    //READ
    @GetMapping("/{participantId}")
    public ResponseEntity<EventParticipantReadDTO> getParticipantById(
        @PathVariable Long participantId
    ) {

        EventParticipantReadDTO participant = service.getParticipantById(participantId);

        return ResponseEntity.ok(participant);

    }

    @GetMapping
    public ResponseEntity<Page<EventParticipantReadDTO>> getAllParticipants(
        @PathVariable Long eventId,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<EventParticipantReadDTO> participants = service.getAllParticipants(
            eventId,
            searchQuery,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok(participants);

    }

}
