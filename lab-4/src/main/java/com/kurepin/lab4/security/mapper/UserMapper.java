package com.kurepin.lab4.security.mapper;

import com.kurepin.lab4.entities.Employee;
import com.kurepin.lab4.security.dto.UserDto;
import com.kurepin.lab4.security.models.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
@Component
public class UserMapper {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public User toEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
