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
        var revokedToken = new RevokedToken(token);
        revokedTokenRepository.save(revokedToken);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.existsByToken(token);
    }

}
