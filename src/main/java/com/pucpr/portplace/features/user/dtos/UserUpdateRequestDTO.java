package com.pucpr.portplace.features.user.dtos;

import com.pucpr.portplace.core.validation.constraints.enumValues.ValidEnum;
import com.pucpr.portplace.features.user.enums.UserStatusEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private String password;

    @ValidEnum(enumClass = UserStatusEnum.class)
    private String status;

}
