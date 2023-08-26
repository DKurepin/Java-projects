package com.kurepin.service.security.services;

import com.kurepin.service.security.dto.UserDto;
import com.kurepin.service.security.models.User;

import java.text.ParseException;

public interface UserService {
    UserDto saveUser(User user);
    void deleteUserById(Long id);
    UserDto updateUser(User user) throws ParseException;
    UserDto getUserById(Long id) throws ParseException;
}
