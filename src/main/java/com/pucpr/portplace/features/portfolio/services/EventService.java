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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {
    
    private EventRepository repository;
    private PortfolioEntityService portfolioService;
    private EventMapper mapper;

    //CREATE
    public EventReadDTO createEvent(
        Long portfolioId,
        EventCreateDTO dto
    ) {

        //TODO: validate if portfolio exists

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

        //TODO: validate if event exists

        Event event = repository.findById(eventId).get();

        mapper.updateFromDto(dto, event);

        repository.save(event);

        return mapper.toReadDTO(event);

    }

    //DELETE
    public void disableEvent(
        Long eventId
    ) {

        //TODO: validate if event exists

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

            //TODO: validate if event exists
            events = repository.findByParticipant(
                participantId,
                searchQuery,
                includeDisabled,
                pageable
            );

            return events.map(mapper::toReadDTO);

        } else {
            
            //TODO: validate if event exists
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

        //TODO: validate if event exists

        Event event = repository.findById(eventId).get();

        return mapper.toReadDTO(event);

    }

}
