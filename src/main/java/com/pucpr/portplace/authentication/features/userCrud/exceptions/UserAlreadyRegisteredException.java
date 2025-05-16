package com.pucpr.portplace.authentication.features.userCrud.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String email) {
        super("User with email " + email + " already registered");
    }

    public UserAlreadyRegisteredException() {
        super("User already registered");
    }
    
}
