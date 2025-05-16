package com.pucpr.portplace.authentication.features.userCrud.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("User with email " + email + " could not be found");
    }

    public UserNotFoundException() {
        super("User could not be found");
    }

    public UserNotFoundException(Long id) {
        super("User with id " + id + " could not be found");
    }

}
