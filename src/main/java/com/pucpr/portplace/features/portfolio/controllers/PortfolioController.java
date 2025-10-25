package com.pucpr.portplace.features.portfolio.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioAnalyticsReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioCancelationPatchDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioListReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioUpdateDTO;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;
import com.pucpr.portplace.features.portfolio.services.PortfolioService;
import com.pucpr.portplace.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.features.reports.services.ReportService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Portfolio", description = "Related to the Portfolio operations")
@AllArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private PortfolioService portfolioService;
    private ReportService reportService;

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

    @PatchMapping("/{portfolioId}/complete")
    public ResponseEntity<PortfolioReadDTO> completePortfolio(
        @PathVariable long portfolioId
    ) {

        PortfolioReadDTO p = portfolioService.completePortfolio(portfolioId);

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

    
    @GetMapping("/{portfolioId}/analytics")
    public ResponseEntity<PortfolioAnalyticsReadDTO> getAllProjectsUnpaged(
        @PathVariable Long portfolioId,
        @RequestParam(required = false) List<ProjectStatusEnum> status
    ) {

        return ResponseEntity.ok(portfolioService.getAnalytics(portfolioId, status));

    }
    
    @GetMapping("/{portfolioId}/analytics/excel")
    public ResponseEntity<Void> exportAnalyticsToExcel(
        @PathVariable Long portfolioId,
        HttpServletResponse response
    ) {

        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=portfolio_" + portfolioId + ".xlsx");
            reportService.exportDataToExcelTemplate(portfolioId, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().build();
        
    }
    
    @GetMapping("/{portfolioId}/analytics/pdf")
    public ResponseEntity<byte[]> exportPortfolioPdf(@PathVariable Long portfolioId) {
        byte[] pdfBytes = reportService.exportDataToPdfTemplate(portfolioId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=portfolio_" + portfolioId + ".pdf")
                .body(pdfBytes);
    }

}
