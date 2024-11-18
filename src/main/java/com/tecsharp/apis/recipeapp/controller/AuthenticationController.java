package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.AuthRequest;
import com.tecsharp.apis.recipeapp.dto.ErrorResponse;
import com.tecsharp.apis.recipeapp.dto.LoginResponse;
import com.tecsharp.apis.recipeapp.service.revokedtoken.AuthService;
import com.tecsharp.apis.recipeapp.service.user.UserService;
import com.tecsharp.apis.recipeapp.service.user.impl.UserDetailsServiceImpl;
import com.tecsharp.apis.recipeapp.utils.Constants;
import com.tecsharp.apis.recipeapp.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    public static final String CONTROLLER_NAME = "AuthenticationController";

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "authenticate");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwtToken(jwtUtil.generateToken(userDetails.getUsername(), roles));
            loginResponse.setUser(userService.getUserByUsername(authRequest.getUsername()));

            return ResponseEntity.ok(loginResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Authentication failed: " + e.getMessage()));
        }
    }
    @GetMapping("/admin/get/master-token")
    public ResponseEntity<String> getMasterToken() {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "getMasterToken");
        final String masterToken = jwtUtil.generateMasterToken();
        return ResponseEntity.ok(masterToken);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorization) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "logout");
        String token = authorization.substring(7);
        authService.revokeToken(token);
        return ResponseEntity.ok("Desautenticación exitosa");
    }

}
