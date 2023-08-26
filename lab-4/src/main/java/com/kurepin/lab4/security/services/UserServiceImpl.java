package com.kurepin.lab4.security.services;

import com.kurepin.lab4.security.dto.UserDto;
import com.kurepin.lab4.security.mapper.UserMapper;
import com.kurepin.lab4.security.models.User;
import com.kurepin.lab4.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto saveUser(User user) {
        userRepository.save(user);
        UserDto userDto = userMapper.toDto(user);
        return userDto;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(User user) throws ParseException {
        userRepository.save(user);
        UserDto userDto = userMapper.toDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) throws ParseException {
        User user = userRepository.findById(id).orElseGet(() -> userRepository.getReferenceById(id));
        UserDto userDto = userMapper.toDto(user);
        return userDto;
    }
}
