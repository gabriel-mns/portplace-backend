package com.pucpr.portplace.features.portfolio.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.event.EventCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.event.EventUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.Event;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.mappers.EventMapper;
import com.pucpr.portplace.features.portfolio.repositories.EventRepository;
import com.pucpr.portplace.features.portfolio.services.internal.PortfolioEntityService;
import com.pucpr.portplace.features.portfolio.services.validation.EventValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {
    
    private EventRepository repository;
    private PortfolioEntityService portfolioService;
    private EventMapper mapper;
    private EventValidationService validationService;

    //CREATE
    public EventReadDTO createEvent(
        Long portfolioId,
        EventCreateDTO dto
    ) {

        validationService.validateBeforeCreate(portfolioId);

        Event event = mapper.toEntity(dto);
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
        event.setPortfolio(portfolio);

        repository.save(event);

        return mapper.toReadDTO(event);

    }

    //UPDATE
    public EventReadDTO updateEvent(
        Long eventId,
        EventUpdateDTO dto
    ) {

        validationService.validateBeforeUpdate(eventId);

        Event event = repository.findById(eventId).get();

        mapper.updateFromDto(dto, event);

        repository.save(event);

        return mapper.toReadDTO(event);

    }

    //DELETE
    public void disableEvent(
        Long eventId
    ) {

        validationService.validateBeforeDisable(eventId);

        Event event = repository.findById(eventId).get();
        event.setDisabled(true);
        repository.save(event);

    }

    public void deleteEvent(
        Long eventId
    ) {

        repository.deleteById(eventId);

    }

    //READ
    public Page<EventReadDTO> getEvents(
        Long portfolioId,
        Long participantId,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        Page<Event> events;

        if( participantId != null ) {

            validationService.validateBeforeGetByStakeholder(participantId);
            events = repository.findByParticipant(
                participantId,
                searchQuery,
                includeDisabled,
                pageable
            );

            return events.map(mapper::toReadDTO);

        } else {

            validationService.validateBeforeGetAll(portfolioId);
            events = repository.findByFilters(
                portfolioId,
                searchQuery,
                includeDisabled,
                pageable
            );

        }


        return events.map(mapper::toReadDTO);

    }

    public EventReadDTO getEventById(
        Long eventId
    ) {

        validationService.validateBeforeGet(eventId);

        Event event = repository.findById(eventId).get();

        return mapper.toReadDTO(event);

    }

}
