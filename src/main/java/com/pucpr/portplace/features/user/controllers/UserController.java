package com.pucpr.portplace.features.user.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pucpr.portplace.features.user.dtos.UserGetResponseDTO;
import com.pucpr.portplace.features.user.dtos.UserRegisterDTO;
import com.pucpr.portplace.features.user.dtos.UserUpdateRequestDTO;
import com.pucpr.portplace.features.user.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User", description = "Related to the User CRUD operations")
@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {
    
    private final UserService authService;

    public UserController(UserService service) {
        this.authService = service;
    }


    /*
     * 
     * ENDPOINTS
     * 
     */

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterDTO request) {

        return authService.register(request);


    }

    @GetMapping
    public ResponseEntity<List<UserGetResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetResponseDTO> getUserDetails(@PathVariable Long id) {
        return ResponseEntity.ok(authService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDTO user) {

        return authService.updateUser(user, id);
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        
        return authService.deleteUser(id);

    }

}
