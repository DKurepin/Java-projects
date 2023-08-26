package com.kurepin.lab4.security.controller;

import com.kurepin.lab4.security.dto.UserDto;
import com.kurepin.lab4.security.mapper.UserMapper;
import com.kurepin.lab4.security.models.User;
import com.kurepin.lab4.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public UserDto getUserById(@PathVariable("id") Long id) throws ParseException {
        return userService.getUserById(id);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('write')")
    public UserDto saveUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userService.saveUser(user);
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('update')")
    public UserDto updateUser(@RequestBody UserDto userDto) throws ParseException {
        User user = userMapper.toEntity(userDto);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }
}
