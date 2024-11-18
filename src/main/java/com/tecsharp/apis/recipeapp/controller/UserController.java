package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.UserDTO;
import com.tecsharp.apis.recipeapp.service.user.UserService;
import com.tecsharp.apis.recipeapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    Logger log = LoggerFactory.getLogger(UserController.class);
    public static final String CONTROLLER_NAME = "UserController";

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "registerUser");
        userService.registerUser(userDTO);
        log.info("User registered successfully");
        return ResponseEntity.ok(Constants.USER_REGISTERED_SUCCESS);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "deleteUser");
        var user = userService.getUserById(id);
        if (user == null) {
            log.error(Constants.USER_WITH_ID_NOT_FOUND, id);
            return ResponseEntity.badRequest().body(Constants.USER_NOT_FOUND);
        } else {
            userService.deleteUserById(id);
            log.info("User {} deleted successfully", user.getUsername());
            return ResponseEntity.ok(Constants.USER_DELETED_SUCCESS);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "getAllUsers");
        Map<String, Object> usersInfo = new HashMap<>();
        var users = userService.getAllUsers();
        if (users.isEmpty()) {
            log.info(Constants.NO_USERS_FOUND);
            return ResponseEntity.notFound().build();
        } else {
            users.forEach(user -> user.setPassword(null));
            usersInfo.put("users", users);
            log.info("Users found successfully");
            return ResponseEntity.ok(usersInfo);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "updateUser");
        userService.updateUser(id, userDTO);
        var user = userService.getUserById(id);
        if (user == null) {
            log.error(Constants.USER_NOT_UPDATED);
            log.error(Constants.USER_WITH_ID_NOT_FOUND, id);
            return ResponseEntity.badRequest().body(Collections.singletonMap(Constants.ERROR_MESSAGE, Constants.USER_NOT_UPDATED));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", Constants.USER_UPDATED_SUCCESS);
        response.put("user", user);
        log.info("User {} updated successfully", user.getUsername());
        return ResponseEntity.ok(response);
    }
}
