package com.pucpr.portplace.authentication.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.pucpr.portplace.authentication.features.ahp.controllers.AHPController;
import com.pucpr.portplace.authentication.features.ahp.controllers.AHPResultsController;
import com.pucpr.portplace.authentication.features.ahp.controllers.CriteriaComparisonController;
import com.pucpr.portplace.authentication.features.ahp.controllers.CriteriaGroupController;
import com.pucpr.portplace.authentication.features.ahp.controllers.CriterionController;
import com.pucpr.portplace.authentication.features.ahp.controllers.EvaluationController;
import com.pucpr.portplace.authentication.features.ahp.dtos.AHPCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaComparisonCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriteriaGroupCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.CriterionCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.EvaluationCreateDTO;
import com.pucpr.portplace.authentication.features.ahp.dtos.ProjectRankingReadDTO;
import com.pucpr.portplace.authentication.features.ahp.entities.AHP;
import com.pucpr.portplace.authentication.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.authentication.features.ahp.entities.Criterion;
import com.pucpr.portplace.authentication.features.ahp.entities.Strategy;
import com.pucpr.portplace.authentication.features.ahp.enums.ImportanceScale;
import com.pucpr.portplace.authentication.features.ahp.repositories.AHPRepository;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriteriaGroupRepository;
import com.pucpr.portplace.authentication.features.ahp.repositories.CriterionRepository;
import com.pucpr.portplace.authentication.features.ahp.repositories.StrategyRepository;
import com.pucpr.portplace.authentication.features.project.controllers.ProjectController;
import com.pucpr.portplace.authentication.features.project.dtos.ProjectCreateDTO;
import com.pucpr.portplace.authentication.features.project.entities.Project;
import com.pucpr.portplace.authentication.features.project.enums.ProjectStatusEnum;
import com.pucpr.portplace.authentication.features.project.repositories.ProjectRepository;
import com.pucpr.portplace.authentication.features.user.controllers.UserController;
import com.pucpr.portplace.authentication.features.user.dtos.UserRegisterDTO;
import com.pucpr.portplace.authentication.features.user.enums.RoleEnum;

@SpringBootTest
@ActiveProfiles("test")
public class AHPRankingTest {
    
    @Autowired
    private CriteriaGroupController criteriaGroupController;
    @Autowired
    private CriteriaGroupRepository criteriaGroupRepository;
    @Autowired
    private CriterionController criterionController;
    @Autowired
    private CriterionRepository criterionRepository;
    @Autowired
    private CriteriaComparisonController criteriaComparisonController;
    @Autowired
    private AHPController ahpController;
    @Autowired
    private AHPRepository ahpRepository;
    @Autowired
    private EvaluationController evaluationController;
    @Autowired
    private AHPResultsController ahpResultsController;
    @Autowired
    private ProjectController projectController;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserController userController;
    @Autowired
    private StrategyRepository strategyRepository;

    @Test
    void testAHPRanking() {


        // Create a strategy
        createStrategy();

        // Create criteria group
        createCriteriaGroup();
        CriteriaGroup criteriaGroup = criteriaGroupRepository.findById(Long.valueOf(1)).get();

        // Create criteria
        createCriterion(1, criteriaGroup);
        createCriterion(2, criteriaGroup);
        createCriterion(3, criteriaGroup);
        Criterion criterion1 = criterionRepository.findById(Long.valueOf(1)).get();
        Criterion criterion2 = criterionRepository.findById(Long.valueOf(2)).get();
        Criterion criterion3 = criterionRepository.findById(Long.valueOf(3)).get();
        
        // Create criteria comparisons
        createCriteriaComparison(criterion1, criterion2, ImportanceScale.EXTREMELY_MORE_IMPORTANT, criteriaGroup.getId());
        createCriteriaComparison(criterion1, criterion3, ImportanceScale.EXTREMELY_MORE_IMPORTANT, criteriaGroup.getId());
        createCriteriaComparison(criterion2, criterion3, ImportanceScale.MUCH_LESS_IMPORTANT, criteriaGroup.getId());

        // Create projects
        createProject(1);
        createProject(2);
        createProject(3);
        createProject(4);
        Project project1 = projectRepository.findById(Long.valueOf(1)).get();
        Project project2 = projectRepository.findById(Long.valueOf(2)).get();
        Project project3 = projectRepository.findById(Long.valueOf(3)).get();
        Project project4 = projectRepository.findById(Long.valueOf(4)).get();

        // Create AHP
        createAHP(criteriaGroup.getId(), 1);
        AHP ahpCreateDTO = ahpRepository.findById(Long.valueOf(1)).get();

        // Create evaluations
        createEvaluation(project1, criterion1, ahpCreateDTO.getId(), 1000);
        createEvaluation(project1, criterion2, ahpCreateDTO.getId(), 800);
        createEvaluation(project1, criterion3, ahpCreateDTO.getId(), 600);
        createEvaluation(project2, criterion1, ahpCreateDTO.getId(), 900);
        createEvaluation(project2, criterion2, ahpCreateDTO.getId(), 700);
        createEvaluation(project2, criterion3, ahpCreateDTO.getId(), 500);
        createEvaluation(project3, criterion1, ahpCreateDTO.getId(), 100);
        createEvaluation(project3, criterion2, ahpCreateDTO.getId(), 1000);
        createEvaluation(project3, criterion3, ahpCreateDTO.getId(), 200);
        createEvaluation(project4, criterion1, ahpCreateDTO.getId(), 800);
        createEvaluation(project4, criterion2, ahpCreateDTO.getId(), 600);
        createEvaluation(project4, criterion3, ahpCreateDTO.getId(), 400);

        // Calculate AHP results
        List<ProjectRankingReadDTO> ranking = ahpResultsController.getRanking(ahpCreateDTO.getId()).getBody();

        // Verify order and size 
        assertNotNull(ranking);
        assertEquals(4, ranking.size());
        assertEquals(project1.getId(), ranking.get(0).getProjectId());
        assertEquals(project2.getId(), ranking.get(1).getProjectId());
        assertEquals(project4.getId(), ranking.get(2).getProjectId());
        assertEquals(project3.getId(), ranking.get(3).getProjectId());

        // Verify order of positions
        assertTrue(ranking.get(0).getTotalScore() >= ranking.get(1).getTotalScore());
        assertTrue(ranking.get(1).getTotalScore() >= ranking.get(2).getTotalScore());
        assertTrue(ranking.get(2).getTotalScore() >= ranking.get(3).getTotalScore());

        // Valores esperados
        double delta = 1.0;  // tolerância de ±1 ponto
        assertEquals(913.44, ranking.get(0).getTotalScore(), delta, "Score do project1 fora da margem");
        assertEquals(813.44, ranking.get(1).getTotalScore(), delta, "Score do project2 fora da margem");
        assertEquals(713.44, ranking.get(2).getTotalScore(), delta, "Score do project4 fora da margem");
        assertEquals(169.75, ranking.get(3).getTotalScore(), delta, "Score do project3 fora da margem");

        // (Opcional) imprime para debug
        ranking.forEach(pr -> 
            System.out.println("#" + pr.getPosition() +
                " - ID: " + pr.getProjectId() +
                ", Score: " + pr.getTotalScore()));

    }
    
    private void createStrategy() {

        // Create a strategy
        Strategy strategy = Strategy.builder()
                .name("Test Strategy")
                .description("This is a test strategy for AHP ranking.")
                .build();
        strategyRepository.save(strategy);

    }

    private void createCriteriaGroup() {
        CriteriaGroupCreateDTO criteriaGroupCreateDTO = new CriteriaGroupCreateDTO();

        criteriaGroupCreateDTO.setName("Test Criteria Group");
        criteriaGroupCreateDTO.setDescription("This is a test criteria group for AHP ranking.");
        criteriaGroupController.createCriteriaGroup(1, criteriaGroupCreateDTO);

    }

    private void createCriterion(int index, CriteriaGroup criteriaGroup) {
        // Create criteria
        CriterionCreateDTO criterionCreateDTO = new CriterionCreateDTO();
        criterionCreateDTO.setName("Test Criterion" + index);
        criterionCreateDTO.setDescription("This is a test criterion for AHP ranking." + index);
        criterionController.createCriterion(1, criteriaGroup.getId(), criterionCreateDTO);

    }

    private void createCriteriaComparison(Criterion comparedCriterion, Criterion referenceCriterion, ImportanceScale importanceScale, long criteriaGroupId) {
        CriteriaComparisonCreateDTO criteriaComparisonCreateDTO = new CriteriaComparisonCreateDTO();
        criteriaComparisonCreateDTO.setComparedCriterionId(comparedCriterion.getId());
        criteriaComparisonCreateDTO.setReferenceCriterionId(referenceCriterion.getId());
        criteriaComparisonCreateDTO.setImportanceScale(importanceScale.toString());
        criteriaComparisonController.createCriterionComparison(1, criteriaGroupId, criteriaComparisonCreateDTO);
    }

    private void createProject(int index){

        // Create project manager
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail("testuser" + index + "@example.com");
        userRegisterDTO.setPassword("password" + index);
        userRegisterDTO.setName("Test User " + index);
        userRegisterDTO.setRole(RoleEnum.PROJECT_MANAGER);
        userController.register(userRegisterDTO);

        // Create a project
        ProjectCreateDTO projectCreateDTO = new ProjectCreateDTO();
        projectCreateDTO.setName("Test Project " + index);
        projectCreateDTO.setDescription("This is a test project for AHP ranking." + index);
        projectCreateDTO.setEarnedValue(2000);
        projectCreateDTO.setPlannedValue(1500);
        projectCreateDTO.setActualCost(1000.0);
        projectCreateDTO.setBudget(1200.0);
        projectCreateDTO.setStartDate(LocalDate.now());
        projectCreateDTO.setEndDate(LocalDate.now().plusDays(30));
        projectCreateDTO.setPayback(30);
        projectCreateDTO.setStatus(ProjectStatusEnum.IN_PROGRESS.toString());
        projectCreateDTO.setProjectManager(index);
        projectController.createProject(projectCreateDTO);


    }

    private void createAHP(long criteriaGroupId, int index) {

        AHPCreateDTO ahpCreateDTO = new AHPCreateDTO();
        ahpCreateDTO.setName("AHP Test " + index);
        ahpCreateDTO.setDescription("This is a test AHP for ranking projects." + index);
        ahpCreateDTO.setStrategyId(1L);
        ahpCreateDTO.setCriteriaGroupId(criteriaGroupId);
        ahpController.createAHP(1, ahpCreateDTO);

    }

    private void createEvaluation(Project project, Criterion criterion, long ahpId, int score) {
        
        EvaluationCreateDTO evaluationCreateDTO = new EvaluationCreateDTO();
        evaluationCreateDTO.setProjectId(project.getId());
        evaluationCreateDTO.setCriterionId(criterion.getId());
        evaluationCreateDTO.setScore(score);
        evaluationCreateDTO.setAhpId(ahpId);
        evaluationController.createEvaluation(1, evaluationCreateDTO);

    }

}
