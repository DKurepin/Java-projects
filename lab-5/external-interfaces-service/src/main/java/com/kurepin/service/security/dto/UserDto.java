package com.kurepin.service.security.dto;

import com.kurepin.service.security.models.Role;
import com.kurepin.service.security.models.UserStatus;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private UserStatus status;
    private Long employeeId;
}
