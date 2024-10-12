package com.tecsharp.apis.recipeapp.repository.revokedtoken;

import com.tecsharp.apis.recipeapp.model.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long> {
    boolean existsByToken(String token);
}
