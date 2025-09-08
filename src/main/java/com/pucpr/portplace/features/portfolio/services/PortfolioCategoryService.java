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
import com.pucpr.portplace.features.portfolio.services.validation.PortfolioCategoryValidationService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioCategoryService {

    private PortfolioCategoryValidationService validationService;
    private PortfolioCategoryRepository repository;
    private PortfolioCategoryMapper mapper;

    // CREATE
    public PortfolioCategoryReadDTO createCategory(
        Long portfolioId,
        PortfolioCategoryCreateDTO dto
    ) {

        validationService.validateBeforeCreate(portfolioId);

        dto.setPortfolioId(portfolioId);
        PortfolioCategory entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toReadDTO(entity);
        
    }


    // UPDATE
    public PortfolioCategoryReadDTO updateCategory(
        Long categoryId,
        PortfolioCategoryUpdateDTO dto
    ) {

        validationService.validateBeforeUpdate(categoryId);

        PortfolioCategory entity = repository.findById(categoryId).get();

        mapper.updateFromDTO(dto, entity);
        repository.save(entity);

        return mapper.toReadDTO(entity);

    }

    // DELETE

    public void disableCategory(
        Long categoryId
    ) {

        PortfolioCategory entity = repository.findById(categoryId).get();

        entity.setDisabled(true);

        repository.save(entity);

    }

    public void deleteCategory(
        Long categoryId
    ) {

        validationService.validateBeforeDelete(categoryId);

        PortfolioCategory entity = repository.findById(categoryId).get();

        repository.delete(entity);

    }

    // READ
    public PortfolioCategoryReadDTO getCategoryById(
        Long categoryId
    ) {

        validationService.validateBeforeGet(categoryId);

        PortfolioCategory entity = repository.findById(categoryId).get();

        return mapper.toReadDTO(entity);
        
    }

    public Page<PortfolioCategoryReadDTO> getCategoryByPortfolioId(
        Long portfolioId,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        validationService.validateBeforeGetAll(portfolioId);

        Page<PortfolioCategory> entities = repository.findByFilters(portfolioId, searchQuery, includeDisabled, pageable);

        return entities.map(mapper::toReadDTO);

    }
}
