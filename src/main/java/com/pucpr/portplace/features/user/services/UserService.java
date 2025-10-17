package com.pucpr.portplace.features.user.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.pucpr.portplace.features.user.enums.UserStatusEnum;
import com.pucpr.portplace.features.user.exceptions.UserNotFoundException;
import com.pucpr.portplace.features.user.mappers.UserMapper;
import com.pucpr.portplace.features.user.repositories.UserRepository;
import com.pucpr.portplace.features.user.services.validation.UserValidationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper mapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserValidationService validationService;
    private JwtService jwtService;

    // CREATE
    public ResponseEntity<Void> register(@Valid UserRegisterDTO request){

        validationService.validateBeforeRegister(request.getEmail());
        
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

        validationService.validateBeforeLogin(authenticationRequestDTO.getEmail());

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
    public Page<UserGetResponseDTO> getAllUsers(
        boolean includeDisabled,
        String searchQuery, 
        List<UserStatusEnum> status, 
        Pageable pageable
    ) {
        
        Page<User> users = userRepository.findAllByFilters(
            includeDisabled, 
            searchQuery, 
            status, 
            pageable
        );

        return users.map(mapper::toGetResponseDTO);

    }
    
    public UserGetResponseDTO getUserById(@NotNull Long id) {
        
        validationService.validateBeforeGet(id);

        Optional<User> user = userRepository.findById(id);
        
        return mapper.toGetResponseDTO(user.get());

    }

    // UPDATE
    public ResponseEntity<Void> updateUser(@Valid UserUpdateRequestDTO updatedUser, Long userId) {

        validationService.validateBeforeUpdate(userId);

        Optional<User> userSearchResult = userRepository.findById(userId);

        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(updatedUser.getPassword());

        User user = userSearchResult.get();
        user.setName(updatedUser.getName());
        user.setPassword(encryptedPassword);
        user.setStatus(updatedUser.getStatus() != null ? UserStatusEnum.valueOf(updatedUser.getStatus()) : user.getStatus());
        userRepository.save(user);

        return ResponseEntity.noContent().build();

    }

    // DELETE
    public ResponseEntity<Void> disableUser(@NotNull Long id) {
        
        validationService.validateBeforeDisable(id);

        User user = userRepository.findById(id).get();
        user.setDisabled(true);
        userRepository.save(user);

        return ResponseEntity.noContent().build();

    }

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

    public List<UserGetResponseDTO> getAvailableUsersForPortfolioOwners(
        Long portfolioId
    ) {

        List<User> availableUsers = userRepository.findUsersNotOwningPortfolio(portfolioId);

        return availableUsers.stream()
            .map(mapper::toGetResponseDTO)
            .toList();

    }
}
