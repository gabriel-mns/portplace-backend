package com.pucpr.portplace.features.user.services.validation;

import org.springframework.stereotype.Service;

import com.pucpr.portplace.core.validation.specs.EntityIsEnabledSpecification;
import com.pucpr.portplace.features.user.entities.User;
import com.pucpr.portplace.features.user.exceptions.DisabledUserException;
import com.pucpr.portplace.features.user.exceptions.InactiveUserException;
import com.pucpr.portplace.features.user.exceptions.UserAlreadyRegisteredException;
import com.pucpr.portplace.features.user.exceptions.UserNotFoundException;
import com.pucpr.portplace.features.user.services.internal.UserEntityService;
import com.pucpr.portplace.features.user.specs.UserExistsSpecification;
import com.pucpr.portplace.features.user.specs.UserExistsWithEmailSpecification;
import com.pucpr.portplace.features.user.specs.UserIsActiveSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserValidationService {
    
    private UserExistsWithEmailSpecification userExistsWithEmailSpecification;
    private UserIsActiveSpecification userIsActiveSpecification;
    private EntityIsEnabledSpecification entityEnabledSpecification;
    private UserExistsSpecification userExistsSpecification;
    private UserEntityService userEntityService;

    public void validateBeforeGet(Long id) {

        if(!userExistsSpecification.isSatisfiedBy(id)) {
            throw new UserNotFoundException(id);
        }

    }

    public void validateBeforeUpdate(Long id) {

        validateBeforeGet(id);

    }

    public void validateBeforeDisable(Long id) {

        validateBeforeGet(id);

    }

    public void validateBeforeRegister(String email) {

        if(userExistsWithEmailSpecification.isSatisfiedBy(email)) {
            throw new UserAlreadyRegisteredException(email);
        }

    }

    public void validateBeforeLogin(String email) {

        if(!userExistsWithEmailSpecification.isSatisfiedBy(email)) {
            throw new UserNotFoundException(email);
        }

        User user = userEntityService.getUserByEmailEntity(email);
        
        if(!entityEnabledSpecification.isSatisfiedBy(user)){
            throw new DisabledUserException(email);
        };

        if(!userIsActiveSpecification.isSatisfiedBy(user)) {
            throw new InactiveUserException(email);
        }

    }

}
