package com.gymapp.gymapp.model;

import lombok.Getter;

@Getter
public enum RoleUser {
    ADMIN("ADMIN"),USER("USER");

    private String name;

    RoleUser(String name) {
        this.name = name;
    }
}
