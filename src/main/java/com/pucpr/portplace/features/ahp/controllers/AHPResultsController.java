package com.pucpr.portplace.features.ahp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pucpr.portplace.features.ahp.dtos.ProjectRankingReadDTO;
import com.pucpr.portplace.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.features.ahp.services.AHPResultsService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AHP Results", description = "Related to the AHP results operations")
@RestController
@RequestMapping(StrategyPaths.AHP_RANKING)
public class AHPResultsController {

    @Autowired
    private AHPResultsService ahpResultsService;
    
    @GetMapping
    public ResponseEntity<List<ProjectRankingReadDTO>> getRanking(@PathVariable Long ahpId) {

        List<ProjectRankingReadDTO> ranking = ahpResultsService.getProjectRankingByAHPId(ahpId);

        return ResponseEntity.ok(ranking);

    }

}
