package com.pucpr.portplace.features.portfolio.services;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.repositories.PortfolioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioEntityService {

    private PortfolioRepository repository;

    public void save(Portfolio portfolio) {
        repository.save(portfolio);
    }

    public boolean existsById(Long candidate) {
        return repository.existsById(candidate);
    }
    
}
