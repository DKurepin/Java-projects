package com.kurepin.service.security.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    READ("read"),
    WRITE("write"),
    UPDATE("update");
    @Getter
    private final String permission;

}
