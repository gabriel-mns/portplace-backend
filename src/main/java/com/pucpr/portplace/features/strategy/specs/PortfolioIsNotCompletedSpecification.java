package com.pucpr.portplace.features.strategy.specs;

import org.springframework.stereotype.Component;

import com.pucpr.portplace.core.validation.specs.Specification;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.enums.PortfolioStatusEnum;
import com.pucpr.portplace.features.portfolio.services.PortfolioEntityService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PortfolioIsNotCompletedSpecification implements Specification<Long> {

    private PortfolioEntityService portfolioEntityService;

    @Override
    public boolean isSatisfiedBy(Long portfolioId) {

        Portfolio p = portfolioEntityService.getPortfolioById(portfolioId);

        boolean isCompleted = p.getStatus().equals(PortfolioStatusEnum.COMPLETED);

        return !isCompleted;
    }

}
