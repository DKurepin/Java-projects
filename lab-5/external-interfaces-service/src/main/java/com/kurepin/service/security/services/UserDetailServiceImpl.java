package com.kurepin.service.security.services;

import com.kurepin.service.security.models.SecurityUser;
import com.kurepin.service.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return SecurityUser.fromUser(UserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }
}
