package com.tecsharp.apis.recipeapp.repository.user;

import com.tecsharp.apis.recipeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    void deleteById(Long userId);

    User findByEmail(String email);


}