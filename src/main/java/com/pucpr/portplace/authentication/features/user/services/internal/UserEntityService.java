package com.pucpr.portplace.authentication.features.user.services.internal;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.user.entities.User;
import com.pucpr.portplace.authentication.features.user.exceptions.UserNotFoundException;
import com.pucpr.portplace.authentication.features.user.repositories.UserRepository;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserEntityService {

    private UserRepository userRepository;

    public User getUserByIdEntity(@NotNull Long id) {
        
        Optional<User> user = userRepository.findById(id);
        
        if(!user.isPresent()) throw new UserNotFoundException(id);
        
        return user.get();

    }

    public boolean existsById(long userId) {
        
        return userRepository.existsById(userId);
        
    }


}
