package com.pucpr.portplace.features.ahp.specs;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.ahp.entities.CriteriaComparison;
import com.pucpr.portplace.features.ahp.entities.CriteriaGroup;
import com.pucpr.portplace.features.ahp.entities.Criterion;

@Component
public class AllCriteriaComparedSpecification implements Specification<CriteriaGroup> {
    
    @Override
    public boolean isSatisfiedBy(CriteriaGroup criteriaGroup) {

        return allCriteriaCompared(criteriaGroup.getCriteria(), criteriaGroup.getCriteriaComparisons());

    }

    public boolean allCriteriaCompared(
        List<Criterion> criteriaList,
        List<CriteriaComparison> criteriaComparisonList
    ) {
        // 1. Creates a set of all unique pairs of compared criteria (ignoring disabled comparisons)
        Set<Set<Long>> comparedCriterionPairs = criteriaComparisonList.stream()
            .filter(criterionComparison -> !criterionComparison.isDisabled())
            .map(criterionComparison -> {
                Long id1 = criterionComparison.getReferenceCriterion().getId();
                Long id2 = criterionComparison.getComparedCriterion().getId();
                return Set.of(id1, id2); // creates a pair of IDs ignoring the order (A,B) == (B,A)
            })
            .collect(Collectors.toSet());

        int criteriaCount = criteriaList.size();

        // 2. Run through all possible pairs of criteria
        for (int i = 0; i < criteriaCount; i++) {
            for (int j = i + 1; j < criteriaCount; j++) {
                Long id1 = criteriaList.get(i).getId();
                Long id2 = criteriaList.get(j).getId();
                Set<Long> parEsperado = Set.of(id1, id2);

                // 3. Checks if the expected pair is in the list of comparisons
                if (!comparedCriterionPairs.contains(parEsperado)) {
                    return false; // if any pair is missing, return false
                }
            }
        }

        return true; // all pairs were compared
    }

}
