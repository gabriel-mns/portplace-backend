package com.pucpr.portplace.features.portfolio.services.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.PortfolioReadDTO;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.mappers.PortfolioMapper;
import com.pucpr.portplace.features.portfolio.repositories.PortfolioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioEntityService {

    private PortfolioRepository repository;
    private PortfolioMapper portfolioMapper;

    public Portfolio save(Portfolio portfolio) {
        return repository.save(portfolio);
    }

    public boolean existsById(Long candidate) {
        return repository.existsById(candidate);
    }

    public Portfolio getPortfolioById(Long portfolioId) {
        return repository.findById(portfolioId).orElseThrow();
    }

    public Page<PortfolioReadDTO> findByStrategicObjectiveId(
        long objectiveId,
        String searchQuery,
        PageRequest pageable
    ) {
        
        Page<Portfolio> portfolios = repository.findByObjectiveId(
            objectiveId,
            searchQuery,
            pageable
        );

        return portfolios.map(portfolioMapper :: toReadDTO);

    }

}
