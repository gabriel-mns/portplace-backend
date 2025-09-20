package com.pucpr.portplace.features.portfolio.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pucpr.portplace.features.portfolio.dtos.event.EventCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventUpdateDTO;
import com.pucpr.portplace.features.portfolio.services.EventService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

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


@Tag(name = "Events", description = "Related to the Event operations")
@AllArgsConstructor
@RestController
@RequestMapping("/portfolios/{portfolioId}/events")
public class EventController {
    
    private EventService service;

    @PostMapping
    public ResponseEntity<EventReadDTO> createEvent(
        @PathVariable Long portfolioId,
        @RequestBody @Valid EventCreateDTO dto
    ) {

        EventReadDTO createdEvent = service.createEvent(portfolioId, dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdEvent.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdEvent);

    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventReadDTO> updateEvent(
        @PathVariable Long eventId,
        @RequestBody @Valid EventUpdateDTO dto
    ) {

        EventReadDTO updatedEvent = service.updateEvent(eventId, dto);

        return ResponseEntity.ok(updatedEvent);

    }
    
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> disableEvent(
        @PathVariable Long eventId
    ) {

        service.disableEvent(eventId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{eventId}/hard-delete")
    public ResponseEntity<Void> deleteEvent(
        @PathVariable Long eventId
    ) {

        service.deleteEvent(eventId);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventReadDTO> getEventById(
        @PathVariable Long eventId
    ) {

        EventReadDTO event = service.getEventById(eventId);

        return ResponseEntity.ok(event);

    }

    @GetMapping
    public ResponseEntity<Page<EventReadDTO>> getEvents(
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

        var eventsPage = service.getEvents(
            portfolioId,
            null,
            searchQuery,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok(eventsPage);

    }


}
