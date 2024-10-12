package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.ErrorResponse;
import com.tecsharp.apis.recipeapp.dto.UserDetailsResponse;
import com.tecsharp.apis.recipeapp.model.User;
import com.tecsharp.apis.recipeapp.service.user.UserService;
import com.tecsharp.apis.recipeapp.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/api/user")
public class UserDetailsController {

    @Autowired
    private UserService userService;

    @GetMapping("/details")
    public ResponseEntity<UserDetailsResponse> getUserDetails(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        if (user != null) {
            UserDetailsResponse response = new UserDetailsResponse(user.getUsername(), user.getEmail(), user.getRoles());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username) {
        try {
            var user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap(Constants.ERROR_MESSAGE, errorResponse));
        }
    }
}
