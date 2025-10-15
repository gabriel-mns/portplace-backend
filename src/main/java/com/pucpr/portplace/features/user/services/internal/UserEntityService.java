package com.pucpr.portplace.features.user.services.internal;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.features.user.dtos.UserGetResponseDTO;
import com.pucpr.portplace.features.user.entities.User;
import com.pucpr.portplace.features.user.exceptions.UserNotFoundException;
import com.pucpr.portplace.features.user.repositories.UserRepository;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserEntityService {

    private UserRepository userRepository;
    private UserMapper mapper;

    public User getUserByIdEntity(@NotNull Long id) {
        
        Optional<User> user = userRepository.findById(id);
        
        if(!user.isPresent()) throw new UserNotFoundException(id);
        
        return user.get();

    }

    public boolean existsById(long userId) {
        
        return userRepository.existsById(userId);
        
    }

    public Page<UserGetResponseDTO> getUsersByPortfolioId(
        Long portfolioId, 
        String searchQuery, 
        boolean includeDisabled, 
        Pageable pageable
    ) {
        
        Page<User> owners = userRepository.findByPortfolioIdAndFilters(portfolioId, searchQuery, includeDisabled, pageable);
        
        return owners.map(mapper::toGetResponseDTO);

    }


}
