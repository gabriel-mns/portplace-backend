package com.pucpr.portplace.features.strategy.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;
import com.pucpr.portplace.features.portfolio.services.internal.PortfolioEntityService;
import com.pucpr.portplace.features.project.entities.Project;
import com.pucpr.portplace.features.project.mappers.ProjectMapper;
import com.pucpr.portplace.features.project.services.internal.ProjectEntityService;
import com.pucpr.portplace.features.strategy.dtos.ScenarioAuthorizationPreviewDTO;
// import com.pucpr.portplace.features.ahp.services.internal.EvaluationGroupEntityService;
import com.pucpr.portplace.features.strategy.dtos.ScenarioCreateDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioReadDTO;
import com.pucpr.portplace.features.strategy.dtos.ScenarioUpdateDTO;
import com.pucpr.portplace.features.strategy.entities.Scenario;
import com.pucpr.portplace.features.strategy.entities.ScenarioRanking;
import com.pucpr.portplace.features.strategy.enums.ScenarioStatusEnum;
import com.pucpr.portplace.features.strategy.mappers.ScenarioMapper;
import com.pucpr.portplace.features.strategy.repositories.ScenarioRepository;
import com.pucpr.portplace.features.strategy.services.internal.ScenarioRankingEntityService;
import com.pucpr.portplace.features.strategy.services.validations.ScenarioValidationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScenarioService {
    
    private ScenarioRankingService rankingService;
    private ScenarioRankingEntityService scenarioRankingEntityService;
    private PortfolioEntityService portfolioService;
    private ProjectEntityService projectService;

    private ScenarioRepository repository;
    private ScenarioMapper mapper;
    private ProjectMapper projectMapper;
    private ScenarioValidationService validationService;

    //#region SCENARIO
    //CREATE
    public ScenarioReadDTO createScenario(
        @Valid ScenarioCreateDTO dto,
        long strategyId
    ) {
    
        validationService.validateBeforeCreation(
            strategyId, 
            dto.getEvaluationGroupId(),
            dto.getPortfolioId()
        );

        //Gets the ranked projects from the evaluation group
        long egId = dto.getEvaluationGroupId();
        List<ScenarioRanking> rankings = rankingService.calculateRankingsOnCreation(egId, dto.getBudget());
        
        dto.setStrategyId(strategyId);
        
        // Saves the new scenario
        Scenario newScenario = mapper.toEntity(dto);
        newScenario = repository.save(newScenario);
        
        // Adds the rankings to the scenario and saves again
        newScenario.addScenariosRankings(rankings);
        newScenario = repository.save(newScenario);
        
        return mapper.toReadDTO(newScenario);

    }

    //UPDATE
    public ScenarioReadDTO updateScenario(
        @Valid ScenarioUpdateDTO dto,
        long scenarioId
    ) {
        
        validationService.validateBeforeUpdate(scenarioId);
        
        Scenario scenario = repository.findById(scenarioId).get();

        mapper.updateFromDTO(dto, scenario);

        scenarioRankingEntityService.evaluateScenariosBasedOnBudget(scenario.getScenarioRankings(), scenario.getBudget());
        scenarioRankingEntityService.recalculateCurrentPositions(scenario.getScenarioRankings());

        scenario = repository.save(scenario);

        return mapper.toReadDTO(scenario);

    }

    //DELETE
    public void disableScenario(
        long scenarioId
    ) {
        
        validationService.validateBeforeDisable(scenarioId);

        Scenario scenario = repository.findById(scenarioId).get();

        scenario.setDisabled(true);
        scenario.setStatus(ScenarioStatusEnum.CANCELLED);

        scenario = repository.save(scenario);

    }

    public void deleteScenario(
        long scenarioId
    ) {
        
        repository.deleteById(scenarioId);

    }

    //READ
    public ScenarioReadDTO getScenarioById(
        long scenarioId
    ) {

        validationService.validateBeforeGet(scenarioId);

        Scenario s = repository.findById(scenarioId).get();

        return mapper.toReadDTO(s);

    }

    public Page<ScenarioReadDTO> getAllScenarios(
        long strategyId,
        String name,
        List<ScenarioStatusEnum> status,
        boolean includeDisabled,
        Pageable pageable
    ) {

        validationService.validateBeforeGetAll(strategyId);

        Page<Scenario> scenarios = repository.findByFilters(
            strategyId, 
            name, 
            status, 
            pageable, 
            includeDisabled
        );

        return scenarios.map(mapper::toReadDTO);
    }


    //#region SCENARIO AUTHORIZATION

    public ScenarioAuthorizationPreviewDTO getAuthorizationPreview(
        long scenarioId
    ) {

        validationService.validateBeforeAuthorization(scenarioId);

        Scenario scenario = repository.findById(scenarioId).get();
        ScenarioAuthorizationPreviewDTO dto = mapper.toAuthorizationPreviewDTO(scenario);

        // ALL PROJECTS WITH STATUS INCLUDED OR MANUALLY_INCLUDED
        List<Project> includedProjects = scenario.getIncludedProjects();

        // ALL PROJECTS CURRENTLY IN THE PORTFOLIO THAT WILL BE REMOVED (NOT IN INCLUDED IN SCENARIO)
        List<Project> removedProjects = scenario.getRemovedProjects();

        dto.setIncludedProjects(projectMapper.toReadDTO(includedProjects));
        dto.setRemovedProjects(projectMapper.toReadDTO(removedProjects));

        return dto;

    }

    public void authorizeScenario(
        long scenarioId
    ) {
        
        validationService.validateBeforeAuthorization(scenarioId);

        Scenario scenario = repository.findById(scenarioId).get();
        Portfolio portfolio = portfolioService.getPortfolioById(scenario.getPortfolio().getId());

        List<Project> projectsToBeRemoved = scenario.getRemovedProjects();
        List<Project> projectsToBeIncluded = scenario.getNewIncludedProjects();

        portfolio.setBudget(scenario.getBudget());
        portfolio.setStatus(PortfolioStatusEnum.IN_PROGRESS);
        portfolio.setStrategy(scenario.getStrategy());
        
        portfolio = portfolioService.save(portfolio);
        
        removeProjects(projectsToBeRemoved);
        addProjectsToPortfolio(projectsToBeIncluded, portfolio);

        scenario.setPortfolio(portfolio);
        scenario.setAuthorizationDate(LocalDateTime.now());
        scenario.setStatus(ScenarioStatusEnum.AUTHORIZED);
        repository.save(scenario);

    }

    private void removeProjects(List<Project> projects) {
        for (Project p : projects) {
            p.setPortfolio(null);
            projectService.save(p);
        }

    }

    private void addProjectsToPortfolio(List<Project> projects, Portfolio portfolio) {
        for (Project p : projects) {
            p.setPortfolio(portfolio);
            projectService.save(p);
        }

    }

    //#endregion SCENARIO AUTHORIZATION


}
