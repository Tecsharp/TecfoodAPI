package com.tecsharp.apis.recipeapp.service.user.impl;


import com.tecsharp.apis.recipeapp.dto.UserDTO;
import com.tecsharp.apis.recipeapp.enums.UserRole;
import com.tecsharp.apis.recipeapp.model.User;
import com.tecsharp.apis.recipeapp.repository.user.UserRepository;
import com.tecsharp.apis.recipeapp.service.user.UserService;
import com.tecsharp.apis.recipeapp.utils.Constants;
import com.tecsharp.apis.recipeapp.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserByUsername(String username) {
        var user = userRepository.findByUsername(username);
        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());
        user.setRoles(roles);

        return user;
    }

    @Override
    public User getUserById(Long usernameId) {
        return userRepository.findById(usernameId).orElse(null);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        var users = userRepository.findAll();
        var userDTOS = new ArrayList<UserDTO>();

        for (User user : users) {
            var userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());
            userDTO.setPrivilege(user.getRole().equals(UserRole.ADMIN) ? "yes" : "no");
            userDTO.setLastname(user.getLastname());
            userDTO.setRole(user.getRole().name());
            userDTO.setDateCreation(user.getDateCreation().toString());
            userDTO.setDateModification(user.getDateModification().toString());
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Override
    public String configureFullName(User user) {
        return user.getName() + " " + user.getLastname();
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        var user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDTO.getName());
            user.setLastname(userDTO.getLastname());
            user.setEmail(userDTO.getEmail());
            user.setRole(userDTO.getPrivilege().equals("yes") ? UserRole.ADMIN : UserRole.USER);
            user.setUsername(userDTO.getUsername());
            userRepository.save(user);
            user.setPassword(null);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void registerUser(UserDTO userDTO) throws IllegalArgumentException {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new IllegalArgumentException(Constants.USER_ALREADY_EXIST);
        }

        var user = new User();
        user.setRole(userDTO.getPrivilege().equals("yes") ? UserRole.ADMIN : UserRole.USER);
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setDateCreation(Utils.getCurrentDate());
        user.setDateModification(Utils.getCurrentDate());
        userRepository.save(user);
    }
}