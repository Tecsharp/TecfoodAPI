package com.tecsharp.apis.recipeapp.service.user;

import com.tecsharp.apis.recipeapp.dto.UserDTO;
import com.tecsharp.apis.recipeapp.model.User;

import java.util.List;

public interface UserService {

    User getUserByUsername(String usernameId);

    User getUserById(Long usernameId);

    List<UserDTO> getAllUsers();

    String configureFullName(User user);

    User updateUser(Long user, UserDTO userDTO);

    void deleteUserById(Long userId);

    void registerUser(UserDTO userDTO) throws IllegalArgumentException;

}