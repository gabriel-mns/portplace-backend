package com.pucpr.portplace.authentication.features.ahp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pucpr.portplace.authentication.features.ahp.dtos.ProjectRankingReadDTO;
import com.pucpr.portplace.authentication.features.ahp.paths.StrategyPaths;
import com.pucpr.portplace.authentication.features.ahp.services.AHPResultsService;

@RestController
@RequestMapping(StrategyPaths.AHP_RANKING)
public class AHPResultsController {

    @Autowired
    private AHPResultsService ahpResultsService;
    
    @GetMapping
    public ResponseEntity<List<ProjectRankingReadDTO>> getRanking(@PathVariable Long ahpId) {

        return ahpResultsService.getProjectRankingByAHPId(ahpId);

    }

}
