package com.pucpr.portplace.features.portfolio.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.PortfolioCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;
import com.pucpr.portplace.features.portfolio.mappers.PortfolioMapper;
import com.pucpr.portplace.features.portfolio.repositories.PortfolioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioService {

    private PortfolioRepository repository;
    private PortfolioMapper mapper;

    // CREATE
    public PortfolioReadDTO createPortfolio(
        PortfolioCreateDTO dto
    ){
        
        //TODO: validate
        Portfolio newPortfolio = mapper.toEntity(dto);
        repository.save(newPortfolio);
        return mapper.toReadDTO(newPortfolio);

    }

    // UPDATE
    public PortfolioReadDTO updatePortfolio(
        Long id,
        PortfolioUpdateDTO dto
    ){

        // TODO: validate

        Portfolio existingPortfolio = repository.findById(id).get();

        mapper.updateFromDTO(dto, existingPortfolio);

        repository.save(existingPortfolio);

        return mapper.toReadDTO(existingPortfolio);
    }

    // DELETE
    public void disablePortfolio(
        Long id
    ){
        Portfolio existingPortfolio = repository.findById(id).get();

        existingPortfolio.setDisabled(false);

        repository.save(existingPortfolio);
    }

    public void deletePortfolio(Long id) {

        repository.deleteById(id);

    }


    // READ
    public PortfolioReadDTO getPortfolio(
        Long id
    ) {

        // TODO: validate

        Portfolio existingPortfolio = repository.findById(id).get();

        return mapper.toReadDTO(existingPortfolio);
    }

    public Page<PortfolioReadDTO> getPortfolios(
        List<PortfolioStatusEnum> status,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        // TODO: validate

        Page<Portfolio> portfolios = repository.findByFilters(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return portfolios.map(mapper::toReadDTO);

    }

}
