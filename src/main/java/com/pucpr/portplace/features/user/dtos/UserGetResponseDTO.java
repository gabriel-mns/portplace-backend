package com.pucpr.portplace.features.user.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    // Auditing fields
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;
    private boolean disabled;

}
