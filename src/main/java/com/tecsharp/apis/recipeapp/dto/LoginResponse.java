package com.tecsharp.apis.recipeapp.dto;

import com.tecsharp.apis.recipeapp.model.User;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private User user;
    private String jwtToken;
}
