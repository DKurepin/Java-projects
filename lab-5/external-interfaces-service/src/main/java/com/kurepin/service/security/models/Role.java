package com.kurepin.service.security.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@RequiredArgsConstructor
public enum Role {
    USER(Set.of(Permission.READ, Permission.UPDATE)),
    ADMIN(Set.of(Permission.READ, Permission.WRITE, Permission.UPDATE));
    @Getter
    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream().
                map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(java.util.stream.Collectors.toSet());
    }
}
