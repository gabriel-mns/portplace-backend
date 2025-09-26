package com.pucpr.portplace.features.portfolio.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.entities.PortfolioCategory;
import com.pucpr.portplace.features.portfolio.repositories.PortfolioCategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioCategoryEntityService {
    
    private PortfolioCategoryRepository repository;

    public boolean existsById(Long categoryId) {
        return repository.existsById(categoryId);
    }

    public PortfolioCategory getById(Long categoryId) {
        return repository.findById(categoryId).get();
    }

    public PortfolioCategory getPortfolioCategoryEntityById(Long portfolioCategoryId) {
        return repository.findById(portfolioCategoryId).get();
    }

}
