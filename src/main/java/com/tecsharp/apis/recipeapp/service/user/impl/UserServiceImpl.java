package com.tecsharp.apis.recipeapp.service.user.impl;

import com.tecsharp.apis.recipeapp.dto.UserDTO;
import com.tecsharp.apis.recipeapp.enums.UserRole;
import com.tecsharp.apis.recipeapp.model.User;
import com.tecsharp.apis.recipeapp.repository.user.UserRepository;
import com.tecsharp.apis.recipeapp.service.user.UserService;
import com.tecsharp.apis.recipeapp.utils.Constants;
import com.tecsharp.apis.recipeapp.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getUserByUsername(String username) {
        try {
            var user = userRepository.findByUsername(username);
            if (user == null) {
                log.error("User with username: {} not found", username);
                return null;
            }
            List<String> roles = new ArrayList<>();
            roles.add(user.getRole().name());
            user.setRoles(roles);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user by username" + e.getMessage(), e);
        }
    }

    @Override
    public User getUserById(Long usernameId) {
        try {
            return userRepository.findById(usernameId).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user by ID" + e.getMessage(), e);
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all users" + e.getMessage(), e);
        }
    }

    @Override
    public String configureFullName(User user) {
        try {
            return user.getName() + " " + user.getLastname();
        } catch (Exception e) {
            throw new RuntimeException("Error configuring full name" + e.getMessage(), e);
        }
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        var user = userRepository.findById(id).orElse(null);
        try {
            if (user != null) {
                user.setName(userDTO.getName());
                user.setLastname(userDTO.getLastname());
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                user.setEmail(userDTO.getEmail());
                user.setRole(userDTO.getPrivilege().equals("yes") ? UserRole.ADMIN : UserRole.USER);
                user.setUsername(userDTO.getUsername());
                userRepository.save(user);
                user.setPassword(null);
                return user;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating user" + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void deleteUserById(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user" + e.getMessage(), e);
        }
    }

    @Override
    public void registerUser(UserDTO userDTO) throws IllegalArgumentException {
        try {
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
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error registering user" + e.getMessage(), e);
        }
    }
}
