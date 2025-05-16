package com.pucpr.portplace.authentication.features.userCrud.dtos;

import com.pucpr.portplace.authentication.features.userCrud.entities.User;
import com.pucpr.portplace.authentication.features.userCrud.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGetResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public static UserGetResponseDTO map(User user) {
        
        UserGetResponseDTO newUser = UserGetResponseDTO.builder()
                                                        .id(user.getId())
                                                        .name(user.getName())
                                                        .email(user.getEmail())
                                                        .role(user.getRole())
                                                        .build();

        return newUser;

    }

}
