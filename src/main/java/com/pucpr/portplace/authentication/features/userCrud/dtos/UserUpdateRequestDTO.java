package com.pucpr.portplace.authentication.features.userCrud.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {

    private long id;

    @Valid
    @NotBlank(message = "name is mandatory")
    @NotNull(message = "name is mandatory")
    private String name;

    @NotBlank(message = "password is mandatory")
    @NotNull(message = "password is mandatory")
    @Size(min = 5, message = "password must have at least 5 characters")
    private String password;

}
