package com.kurepin.service.security.controller;

import com.kurepin.service.security.dto.UserDto;
import com.kurepin.service.security.mapper.UserMapper;
import com.kurepin.service.security.models.User;
import com.kurepin.service.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
