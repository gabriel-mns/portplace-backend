package com.pucpr.portplace.features.ahp.specs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.ahp.entities.EvaluationGroup;
import com.pucpr.portplace.features.ahp.entities.Criterion;
import com.pucpr.portplace.features.ahp.entities.Evaluation;
import com.pucpr.portplace.features.project.entities.Project;

@Component
public class AllProjectsEvaluatedSpecification implements Specification<EvaluationGroup> {

    @Override
    public boolean isSatisfiedBy(EvaluationGroup eg) {

        List<Criterion> criteriaList = eg.getCriteriaGroup().getCriteria();

        // Evaluations by project
        Map<Project, List<Evaluation>> evaluationsByProject = eg.getEvaluations()
            .stream()
            .filter(evaluation -> !evaluation.getProject().isDisabled())
            .collect(Collectors.groupingBy(Evaluation::getProject));

        // For each project, check if all criteria have been evaluated
        for (Map.Entry<Project, List<Evaluation>> entry : evaluationsByProject.entrySet()) {

            // Get criteria evaluated for the project
            List<Criterion> evaluatedCriteria = entry.getValue().stream()
                .filter(evaluation -> !evaluation.isDisabled())
                .map(Evaluation::getCriterion)
                .distinct()
                .toList();

            if (!evaluatedCriteria.containsAll(criteriaList)) {
                return false;
            }

        }

        return true;
    }
    
}
