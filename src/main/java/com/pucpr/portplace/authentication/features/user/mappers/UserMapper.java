package com.pucpr.portplace.authentication.features.user.mappers;

import org.mapstruct.Mapper;

import com.pucpr.portplace.authentication.features.user.dtos.UserGetResponseDTO;
import com.pucpr.portplace.authentication.features.user.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserGetResponseDTO toGetResponseDTO(User user);

}
