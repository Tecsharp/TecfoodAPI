package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.TokenRequest;
import com.tecsharp.apis.recipeapp.utils.Constants;
import com.tecsharp.apis.recipeapp.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    Logger log = LoggerFactory.getLogger(TokenController.class);
    public static final String CONTROLLER_NAME = "TokenController";

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestBody TokenRequest tokenRequest) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "validateToken");
        String token = tokenRequest.getToken();
        String username = jwtUtil.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        boolean isValid = jwtUtil.validateToken(token, userDetails);
        if (!isValid) {
            log.error("Token is invalid");
        }
        log.info("Token is valid: {}", isValid);
        return ResponseEntity.ok(isValid);
    }
}

