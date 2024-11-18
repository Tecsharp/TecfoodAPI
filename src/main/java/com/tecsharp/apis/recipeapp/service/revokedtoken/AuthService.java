package com.tecsharp.apis.recipeapp.service.revokedtoken;

import com.tecsharp.apis.recipeapp.model.RevokedToken;
import com.tecsharp.apis.recipeapp.repository.revokedtoken.RevokedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    public void revokeToken(String token) {
        try {
            var revokedToken = new RevokedToken(token);
            revokedTokenRepository.save(revokedToken);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while revoking the token" + e.getMessage(), e);
        }
    }

    public boolean isTokenRevoked(String token) {
        try {
            return revokedTokenRepository.existsByToken(token);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while checking if the token is revoked" + e.getMessage(), e);
        }
    }

}
