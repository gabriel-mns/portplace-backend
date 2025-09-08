package com.pucpr.portplace.features.portfolio.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCategoryUpdateDTO;
import com.pucpr.portplace.features.portfolio.services.PortfolioCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@Tag(name = "PortfolioCategory", description = "Related to the Portfolio Categories operations")
@AllArgsConstructor
@RestController
@RequestMapping("/portfolios/{portfolioId}/categories")
public class PortfolioCategoryController {
    
    private PortfolioCategoryService service;

    // CREATE
    @PostMapping
    public ResponseEntity<PortfolioCategoryReadDTO> createCategory(
        @PathVariable Long portfolioId,
        @RequestBody @Valid PortfolioCategoryCreateDTO dto
    ) {
        
        PortfolioCategoryReadDTO createdCategory = service.createCategory(portfolioId, dto);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdCategory.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdCategory);

    }

    // UPDATE
    @PutMapping("/{categoryId}")
    public ResponseEntity<PortfolioCategoryReadDTO> updateCategory(
        @PathVariable Long categoryId,
        @RequestBody @Valid PortfolioCategoryUpdateDTO dto
    ) {

        PortfolioCategoryReadDTO updatedCategory = service.updateCategory(categoryId, dto);

        return ResponseEntity.ok(updatedCategory);

    }

    // DELETE
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> disableCategory(
        @PathVariable Long categoryId
    ) {

        service.disableCategory(categoryId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{categoryId}/hard-delete")
    public ResponseEntity<Void> deleteCategory(
        @PathVariable Long categoryId
    ) {

        service.deleteCategory(categoryId);

        return ResponseEntity.noContent().build();

    }

    // READ
    @GetMapping("/{categoryId}")
    public ResponseEntity<PortfolioCategoryReadDTO> getCategoryById(
        @PathVariable Long categoryId
    ) {

        PortfolioCategoryReadDTO category = service.getCategoryById(categoryId);

        return ResponseEntity.ok(category);

    }

    @GetMapping
    public ResponseEntity<Page<PortfolioCategoryReadDTO>> getCategories(
        @PathVariable Long portfolioId,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<PortfolioCategoryReadDTO> categoriesPage = service.getCategoryByPortfolioId(
            portfolioId, 
            searchQuery, 
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok().body(categoriesPage);

    }

}
