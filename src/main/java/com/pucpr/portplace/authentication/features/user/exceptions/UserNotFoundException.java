package com.pucpr.portplace.authentication.features.user.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String email) {
        super("User could not be found with email: " + email);
    }

    public UserNotFoundException() {
        super("User could not be found");
    }

    public UserNotFoundException(Long id) {
        super("User could not be found with ID: " + id);
    }

}
