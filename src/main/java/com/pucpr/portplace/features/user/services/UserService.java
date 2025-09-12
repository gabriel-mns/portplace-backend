package com.pucpr.portplace.features.user.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pucpr.portplace.core.security.services.JwtService;
import com.pucpr.portplace.features.user.dtos.AuthenticationRequestDTO;
import com.pucpr.portplace.features.user.dtos.AuthenticationResponseDTO;
import com.pucpr.portplace.features.user.dtos.UserGetResponseDTO;
import com.pucpr.portplace.features.user.dtos.UserRegisterDTO;
import com.pucpr.portplace.features.user.dtos.UserUpdateRequestDTO;
import com.pucpr.portplace.features.user.entities.User;
import com.pucpr.portplace.features.user.exceptions.UserAlreadyRegisteredException;
import com.pucpr.portplace.features.user.exceptions.UserNotFoundException;
import com.pucpr.portplace.features.user.repositories.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    // CREATE
    public ResponseEntity<Void> register(@Valid UserRegisterDTO request){

        boolean userExists = userRepository.existsByEmail(request.getEmail());

        if(userExists) throw new UserAlreadyRegisteredException(request.getEmail());
        
        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        var user = new User(
            request.getName(),
            request.getEmail(),
            encryptedPassword,
            request.getRole()
        );

        userRepository.save(user).getId();
        
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    public AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword()
        );

        authenticationManager.authenticate(authToken);

        User user = userRepository.findByEmail(authenticationRequestDTO.getEmail()).get();

        String token = jwtService.generateToken(user, generateExtraClaims(user));

        System.out.println("User "+ user.getEmail() +" logged in");
        System.out.println("Authorities:"+ user.getAuthorities());

        return new AuthenticationResponseDTO(token);

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

        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(updatedUser.getPassword());

        User user = userSearchResult.get();
        user.setName(updatedUser.getName());
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        return ResponseEntity.noContent().build();

    }

    // DELETE
    public ResponseEntity<Void> deleteUser(@NotNull Long id) {
        
        if(!userRepository.existsById(id)) throw new UserNotFoundException(id);

        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();

    }


    // EXTRA
    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        
        extraClaims.put("email", user.getEmail());
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        
        return extraClaims;
        
    }
}
