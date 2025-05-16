package com.pucpr.portplace.authentication.features.userCrud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.authentication.features.userCrud.dtos.UserGetResponseDTO;
import com.pucpr.portplace.authentication.features.userCrud.dtos.UserRegisterDto;
import com.pucpr.portplace.authentication.features.userCrud.dtos.UserUpdateRequestDTO;
import com.pucpr.portplace.authentication.features.userCrud.entities.User;
import com.pucpr.portplace.authentication.features.userCrud.exceptions.UserAlreadyRegisteredException;
import com.pucpr.portplace.authentication.features.userCrud.exceptions.UserNotFoundException;
import com.pucpr.portplace.authentication.features.userCrud.repositories.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    // CREATE
    public ResponseEntity<Void> register(@Valid UserRegisterDTO request){

        boolean userExists = userRepository.existsByEmail(request.getEmail());

        if(userExists) throw new UserAlreadyRegisteredException(request.getEmail());
        
        var user = new User(
            request.getName(),
            request.getEmail(),
            request.getPassword(),
            request.getRole()
        );

        userRepository.save(user).getId();
        
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    // READ
    public List<UserGetResponseDTO> getAllUsers() {
        return userRepository.findAll()
                            .stream()
                            .map(UserGetResponseDTO::map)
                            .collect(Collectors.toList());
    }
    
    public UserGetResponseDTO getUserById(@NotNull Long id) {
        
        Optional<User> user = userRepository.findById(id);
        
        if(!user.isPresent()) throw new UserNotFoundException(id);
        
        return UserGetResponseDTO.map(user.get());

    }

    // UPDATE
    public ResponseEntity<Void> updateUser(@Valid UserUpdateRequestDTO updatedUser, Long userId) {

        Optional<User> userSearchResult = userRepository.findById(userId);

        if(userSearchResult.isEmpty()) throw new UserNotFoundException(userId);

        User user = userSearchResult.get();
        user.setName(updatedUser.getName());
        userRepository.save(user);

        return ResponseEntity.noContent().build();

    }

    // DELETE
    public ResponseEntity<Void> deleteUser(@NotNull Long id) {
        
        if(!userRepository.existsById(id)) throw new UserNotFoundException(id);

        userRepository.deleteById(id);

return ResponseEntity.noContent().build();

    }
    
}
