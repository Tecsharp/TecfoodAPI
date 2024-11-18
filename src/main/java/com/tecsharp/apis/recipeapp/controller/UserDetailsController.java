package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.UserDetailsResponse;
import com.tecsharp.apis.recipeapp.model.User;
import com.tecsharp.apis.recipeapp.service.user.UserService;
import com.tecsharp.apis.recipeapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserDetailsController {

    @Autowired
    private UserService userService;

    Logger log = LoggerFactory.getLogger(UserDetailsController.class);
    public static final String CONTROLLER_NAME = "UserDetailsController";

    @GetMapping("/details")
    public ResponseEntity<UserDetailsResponse> getUserDetails(Principal principal) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "getUserDetails");
        User user = userService.getUserByUsername(principal.getName());
        if (user != null) {
            UserDetailsResponse response = new UserDetailsResponse(user.getUsername(), user.getEmail(), user.getRoles());
            log.info(Constants.USER_DETAILS_FOUND, user.getUsername());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "getUserByUsername");
        var user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        log.info(Constants.USER_WITH_USERNAME_FOUND, username);
        return ResponseEntity.ok(user);
    }
}
