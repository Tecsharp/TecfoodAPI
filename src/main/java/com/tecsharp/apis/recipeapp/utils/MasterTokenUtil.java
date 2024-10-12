package com.tecsharp.apis.recipeapp.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MasterTokenUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateMasterToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "MASTER");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject("MasterToken")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
