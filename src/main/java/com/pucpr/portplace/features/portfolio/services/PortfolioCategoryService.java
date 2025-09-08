package com.pucpr.portplace.features.portfolio.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryUpdateDTO;
import com.pucpr.portplace.features.portfolio.entities.PortfolioCategory;
import com.pucpr.portplace.features.portfolio.mappers.PortfolioCategoryMapper;
import com.pucpr.portplace.features.portfolio.repositories.PortfolioCategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioCategoryService {

    private PortfolioCategoryRepository repository;
    private PortfolioCategoryMapper mapper;

    // CREATE
    public PortfolioCategoryReadDTO createCategory(
        Long portfolioId,
        PortfolioCategoryCreateDTO dto
    ) {
        
        // TODO: validate if portfolio exists

        dto.setPortfolioId(portfolioId);
        PortfolioCategory entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toReadDTO(entity);
        
    }


    // UPDATE
    public PortfolioCategoryReadDTO updateCategory(
        Long id,
        PortfolioCategoryUpdateDTO dto
    ) {
        
        // TODO: validate if category exists

        PortfolioCategory entity = repository.findById(id).get();

        mapper.updateFromDTO(dto, entity);
        repository.save(entity);

        return mapper.toReadDTO(entity);

    }

    // DELETE

    public void disableCategory(
        Long categoryId
    ) {

        // TODO: validate if category exists

        PortfolioCategory entity = repository.findById(categoryId).get();

        entity.setDisabled(true);

        repository.save(entity);

    }

    public void deleteCategory(
        Long categoryId
    ) {

        // TODO: validate if category exists

        PortfolioCategory entity = repository.findById(categoryId).get();

        repository.delete(entity);

    }

    // READ
    public PortfolioCategoryReadDTO getCategoryById(
        Long categoryId
    ) {

        // TODO: validate if category exists

        PortfolioCategory entity = repository.findById(categoryId).get();

        return mapper.toReadDTO(entity);
        
    }

    public Page<PortfolioCategoryReadDTO> getCategoryByPortfolioId(
        Long portfolioId,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        // TODO: validate if portfolio exists

        Page<PortfolioCategory> entities = repository.findByFilters(portfolioId, searchQuery, includeDisabled, pageable);

        return entities.map(mapper::toReadDTO);

    }
}
