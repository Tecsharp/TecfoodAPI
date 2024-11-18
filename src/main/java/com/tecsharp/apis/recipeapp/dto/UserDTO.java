package com.tecsharp.apis.recipeapp.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Data
@ToString(exclude = {"password"})
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String email;

    private String privilege;

    private String name;

    private String role;

    private String lastname;

    private String dateCreation;

    private String dateModification;

    private List<String> roles;

}
