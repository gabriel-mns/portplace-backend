package com.pucpr.portplace.features.portfolio.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class EventNotFoundException extends EntityNotFoundException{
    
    public EventNotFoundException(long id) {
        super("Event could not be found with id: " + id);
    }

}
