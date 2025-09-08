package com.pucpr.portplace.features.portfolio.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class PortfolioCategoryNotFoundException extends EntityNotFoundException{
    
    public PortfolioCategoryNotFoundException(Long categoryId) {
        super("Portfolio Category could not be found with ID: " + categoryId);
    }

}
