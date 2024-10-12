package com.tecsharp.apis.recipeapp.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AuthRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String token;
}
