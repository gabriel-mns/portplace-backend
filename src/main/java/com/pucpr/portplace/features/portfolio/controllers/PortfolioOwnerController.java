package com.pucpr.portplace.features.portfolio.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pucpr.portplace.features.portfolio.dtos.manager.PortfolioOwnersCreateDTO;
import com.pucpr.portplace.features.portfolio.services.PortfolioOwnerService;
import com.pucpr.portplace.features.user.dtos.UserGetResponseDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Portfolio Owners", description = "Related to the Portfolio Owners operations")
@AllArgsConstructor
@RestController
@RequestMapping("/portfolios/{portfolioId}/owners")
public class PortfolioOwnerController {
    
    private PortfolioOwnerService service;

    @PostMapping
    public ResponseEntity<Void> updatePortfolioOwners(
        @PathVariable Long portfolioId, 
        @RequestBody PortfolioOwnersCreateDTO dto
    ) {

        service.updatePortfolioOwners(portfolioId, dto);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<Page<UserGetResponseDTO>> getPortfolioOwners(
        @PathVariable Long portfolioId,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<UserGetResponseDTO> owners = service.getPortfolioOwners(portfolioId, searchQuery, includeDisabled, pageable);
        return ResponseEntity.ok(owners);

    }

}
