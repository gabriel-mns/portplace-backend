package com.pucpr.portplace.features.user.dtos;


import com.pucpr.portplace.features.user.enums.RoleEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

    @Valid
    @NotBlank(message = "name is mandatory")
    @NotNull(message = "name is mandatory")
    private String name;
    
    @Valid
    @NotBlank(message = "email is mandatory")
    @NotNull(message = "email is mandatory")
    @Pattern(regexp = "^(.+)@(.+)$", message = "email must be a valid email")
    private String email;

    @NotBlank(message = "password is mandatory")
    @NotNull(message = "password is mandatory")
    @Size(min = 5, message = "password must have at least 5 characters")
    private String password;
    
    private RoleEnum role;

}
