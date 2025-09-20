package com.pucpr.portplace.features.portfolio.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.portfolio.dtos.manager.PortfolioOwnersCreateDTO;
import com.pucpr.portplace.features.portfolio.entities.Portfolio;
import com.pucpr.portplace.features.portfolio.services.internal.PortfolioEntityService;
import com.pucpr.portplace.features.user.dtos.UserGetResponseDTO;
import com.pucpr.portplace.features.user.entities.User;
import com.pucpr.portplace.features.user.services.internal.UserEntityService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioOwnerService {
    
    private PortfolioEntityService portfolioService;
    private UserEntityService userService;

    public void updatePortfolioOwners(
        Long portfolioId, 
        PortfolioOwnersCreateDTO dto
    ) {

        //TODO: validate if portfolio exists
        //TODO: validate if users exists
        //TODO: validate if users are PMO_ADMIN

        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);

        // Remove owners that are not in the new list
        portfolio.getOwners().removeIf(user -> 
            !dto.getOwnersIds().contains(user.getId())
        );


        // Add new owners
        for (Long userId : dto.getOwnersIds()) {
            
            User user = userService.getUserByIdEntity(userId);
            
            if(portfolio.getOwners().contains(user)) continue;

            portfolio.getOwners().add(userService.getUserByIdEntity(userId));

        }

        portfolioService.save(portfolio);
        
    }

    public Page<UserGetResponseDTO> getPortfolioOwners(
        Long portfolioId, 
        String searchQuery, 
        boolean includeDisabled, 
        Pageable pageable
    ) {
        
        //TODO: Validate if portfolio exists

        Page<UserGetResponseDTO> response = userService.getUsersByPortfolioId(portfolioId, searchQuery, includeDisabled, pageable); 

        return response;

    }

}
