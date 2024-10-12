package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.ErrorResponse;
import com.tecsharp.apis.recipeapp.dto.UserDTO;
import com.tecsharp.apis.recipeapp.service.user.UserService;
import com.tecsharp.apis.recipeapp.utils.Constants;
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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return ResponseEntity.ok(Constants.USER_REGISTERED_SUCCESS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            if (userService.getUserById(id) == null) {
                return ResponseEntity.badRequest().body(Constants.USER_NOT_FOUND);
            } else {
                userService.deleteUserById(id);
                return ResponseEntity.ok(Constants.USER_DELETED_SUCCESS);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        try {
            Map<String, Object> usersInfo = new HashMap<>();
            var users = userService.getAllUsers();
            if (users.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                users.forEach(user -> user.setPassword(null));
                usersInfo.put("users", users);
                return ResponseEntity.ok(usersInfo);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap(Constants.ERROR_MESSAGE, errorResponse));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Map<String, Object> response;
        try {
            var user = userService.updateUser(id, userDTO);
            if (user == null) {
                throw new Exception(Constants.USER_NOT_UPDATED);
            } else {
                response = new HashMap<>();
                response.put("message", Constants.USER_UPDATED_SUCCESS);
                response.put("user", user);
            }
        } catch (Exception e) {
            var errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap(Constants.ERROR_MESSAGE, errorResponse));
        }
        
        return ResponseEntity.ok(response);
    }


}
