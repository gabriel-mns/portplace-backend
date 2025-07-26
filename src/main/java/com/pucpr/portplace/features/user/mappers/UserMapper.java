package com.pucpr.portplace.features.user.mappers;

import org.mapstruct.Mapper;

import com.pucpr.portplace.features.user.dtos.UserGetResponseDTO;
import com.pucpr.portplace.features.user.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserGetResponseDTO toGetResponseDTO(User user);

}
