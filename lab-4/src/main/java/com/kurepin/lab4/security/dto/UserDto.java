package com.kurepin.lab4.security.dto;

import com.kurepin.lab4.security.models.Role;
import com.kurepin.lab4.security.models.UserStatus;
import lombok.Builder;
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
