package com.pucpr.portplace.features.portfolio.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioAnalyticsReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioCancelationPatchDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioCreateDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioListReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioReadDTO;
import com.pucpr.portplace.features.portfolio.dtos.portfolio.PortfolioUpdateDTO;
import com.pucpr.portplace.features.portfolio.dtos.risk.RiskReadDTO;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;
import com.pucpr.portplace.features.portfolio.mappers.PortfolioMapper;
import com.pucpr.portplace.features.portfolio.repositories.PortfolioRepository;
import com.pucpr.portplace.features.portfolio.services.validation.PortfolioValidationService;
import com.pucpr.portplace.features.project.dtos.ProjectReadDTO;
import com.pucpr.portplace.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.features.project.services.ProjectService;
import com.pucpr.portplace.features.strategy.dtos.StrategicObjectiveReadDTO;
import com.pucpr.portplace.features.strategy.services.internal.StrategicObjectiveEntityService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioService {

    private PortfolioValidationService validationService;
    private PortfolioRepository repository;
    private PortfolioMapper mapper;

    private ProjectService projectService;
    private RiskService riskService;
    private StrategicObjectiveEntityService objectivesService;

    // CREATE
    public PortfolioReadDTO createPortfolio(
        PortfolioCreateDTO dto
    ){

        Portfolio newPortfolio = mapper.toEntity(dto);
        repository.save(newPortfolio);
        return mapper.toReadDTO(newPortfolio);

    }

    // UPDATE
    public PortfolioReadDTO updatePortfolio(
        Long id,
        PortfolioUpdateDTO dto
    ){

        validationService.validateBeforeUpdate(id);

        Portfolio existingPortfolio = repository.findById(id).get();

        mapper.updateFromDTO(dto, existingPortfolio);

        repository.save(existingPortfolio);

        return mapper.toReadDTO(existingPortfolio);
    }

    public PortfolioReadDTO cancelPortfolio(
        Long portfolioId,
        PortfolioCancelationPatchDTO dto
    ){

        validationService.validateBeforeCancel(portfolioId);

        Portfolio existingPortfolio = repository.findById(portfolioId).get();

        existingPortfolio.setStatus(PortfolioStatusEnum.CANCELLED);
        existingPortfolio.setCancellationReason(dto.getCancellationReason());

        existingPortfolio = repository.save(existingPortfolio);

        return mapper.toReadDTO(existingPortfolio);

    }

    public PortfolioReadDTO completePortfolio(
        Long portfolioId
    ){

        validationService.validateBeforeComplete(portfolioId);

        Portfolio existingPortfolio = repository.findById(portfolioId).get();

        existingPortfolio.setStatus(PortfolioStatusEnum.COMPLETED);

        existingPortfolio = repository.save(existingPortfolio);

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

        validationService.validateBeforeGet(id);

        Portfolio existingPortfolio = repository.findById(id).get();

        return mapper.toReadDTO(existingPortfolio);
    }

    public Page<PortfolioListReadDTO> getPortfolios(
        List<PortfolioStatusEnum> status,
        String searchQuery,
        boolean includeDisabled,
        Pageable pageable
    ) {

        Page<Portfolio> portfolios = repository.findByFilters(
            status,
            searchQuery,
            includeDisabled,
            pageable
        );

        return portfolios.map(mapper::toListReadDTO);

    }

    public PortfolioAnalyticsReadDTO getAnalytics(
        Long portfolioId, 
        List<ProjectStatusEnum> projectStatus
    ) {
        
        validationService.validateBeforeGet(portfolioId);
        
        List<ProjectReadDTO> projectsDTOs = projectService.getAllProjectsUnpaged(
            portfolioId,
            projectStatus
            );

        List<RiskReadDTO> risksDTOs = riskService.getAllRisksUnpaged(
            portfolioId
        );

        List<StrategicObjectiveReadDTO> objectivesDTOs = objectivesService.findObjectivesByPortfolioId(
            portfolioId
        );
            
        PortfolioAnalyticsReadDTO analyticsDTO = new PortfolioAnalyticsReadDTO();

        analyticsDTO.setPortfolio(getPortfolio(portfolioId));
        analyticsDTO.setProjects(projectsDTOs);
        analyticsDTO.setRisks(risksDTOs);
        analyticsDTO.setStrategicObjectives(objectivesDTOs);

        return analyticsDTO;


    }

}
