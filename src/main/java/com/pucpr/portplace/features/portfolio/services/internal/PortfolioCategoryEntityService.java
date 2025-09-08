package com.pucpr.portplace.features.portfolio.services.internal;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.repositories.PortfolioCategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioCategoryEntityService {
    
    private PortfolioCategoryRepository repository;

    public boolean existsById(Long categoryId) {
        return repository.existsById(categoryId);
    }

}
