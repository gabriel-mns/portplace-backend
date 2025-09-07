package com.pucpr.portplace.features.strategy.exceptions;

import org.springframework.http.HttpStatus;

import com.pucpr.portplace.core.exception.BusinessException;

public class PortfolioAlreadyCompletedException extends BusinessException {
    
    public PortfolioAlreadyCompletedException(Long portfolioId) {
        super("Portfolio with ID: " + portfolioId + " is already completed and cannot be modified.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public PortfolioAlreadyCompletedException(String message) {
        super(message);
    }
    
}
