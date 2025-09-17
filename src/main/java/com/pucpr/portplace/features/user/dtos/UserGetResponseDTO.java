package com.pucpr.portplace.features.user.dtos;

import com.pucpr.portplace.features.user.entities.User;
import com.pucpr.portplace.features.user.enums.RoleEnum;
import com.pucpr.portplace.features.user.enums.UserStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserGetResponseDTO {

    private Long id;
    private String name;
    private String email;
    private UserStatusEnum status;
    private RoleEnum role;

    public static UserGetResponseDTO map(User user) {
        
        UserGetResponseDTO newUser = UserGetResponseDTO.builder()
                                                        .id(user.getId())
                                                        .name(user.getName())
                                                        .email(user.getEmail())
                                                        .status(user.getStatus())
                                                        .role(user.getRole())
                                                        .build();

        return newUser;

    }

}
