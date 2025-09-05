package com.pucpr.portplace.features.portfolio.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class PortfolioNotFoundException extends EntityNotFoundException{

    public PortfolioNotFoundException(Long portfolioId) {
        super("Portfolio could not be found with ID: " + portfolioId);
    }
    
}
