package com.tecsharp.apis.recipeapp.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN(0),
    USER(1),
    MASTER(2);

    private final int role;

    UserRole(int userRole) {
        this.role = userRole;
    }

}