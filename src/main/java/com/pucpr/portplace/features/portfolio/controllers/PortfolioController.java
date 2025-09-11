package com.pucpr.portplace.features.portfolio.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pucpr.portplace.features.portfolio.dtos.PortfolioCancelationPatchDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioListReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.PortfolioUpdateDTO;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;
import com.pucpr.portplace.features.portfolio.services.PortfolioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Portfolio", description = "Related to the Portfolio operations")
@AllArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private PortfolioService portfolioService;

    // CREATE
    @PostMapping
    public ResponseEntity<PortfolioReadDTO> createPortfolio(
        @RequestBody @Valid PortfolioCreateDTO portfolio
    ) {

        PortfolioReadDTO createdPortfolio = portfolioService.createPortfolio(portfolio);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdPortfolio.getId())
            .toUri();

        return ResponseEntity.created(uri).body(createdPortfolio);

    }

    // UPDATE
    @PutMapping("/{portfolioId}")
    public ResponseEntity<PortfolioReadDTO> updatePortfolio(
        @PathVariable long portfolioId,
        @RequestBody @Valid PortfolioUpdateDTO dto
    ) {

        PortfolioReadDTO updatedPortfolio = portfolioService.updatePortfolio(portfolioId, dto);

        return ResponseEntity.ok(updatedPortfolio);

    }

    @PatchMapping("/{portfolioId}/cancel")
    public ResponseEntity<PortfolioReadDTO> cancelPortfolio(
        @PathVariable long portfolioId,
        @RequestBody @Valid PortfolioCancelationPatchDTO portfolio
    ) {

        PortfolioReadDTO p = portfolioService.cancelPortfolio(portfolioId, portfolio);

        return ResponseEntity.ok(p);

    }

    // DELETE
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<Void> disable(
        @PathVariable long portfolioId
    ) {

        portfolioService.disablePortfolio(portfolioId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{portfolioId}/hard-delete")
    public ResponseEntity<Void> deletePortfolio(
        @PathVariable long portfolioId
    ) {

        portfolioService.deletePortfolio(portfolioId);

        return ResponseEntity.noContent().build();

    }

    // READ
    @GetMapping
    public ResponseEntity<Page<PortfolioListReadDTO>> getPortfolios(
        @RequestParam(required = false) List<PortfolioStatusEnum> status,
        @RequestParam(defaultValue = "") String searchQuery,
        @RequestParam(defaultValue = "false") boolean includeDisabled,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<PortfolioListReadDTO> portfolios = portfolioService.getPortfolios(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return ResponseEntity.ok(portfolios);

    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<PortfolioReadDTO> getPortfolio(
        @PathVariable long portfolioId
    ) {

        PortfolioReadDTO portfolio = portfolioService.getPortfolio(portfolioId);

        return ResponseEntity.ok(portfolio);

    }

}
