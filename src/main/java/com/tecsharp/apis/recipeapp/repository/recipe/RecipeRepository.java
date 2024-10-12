package com.tecsharp.apis.recipeapp.repository.recipe;

import com.tecsharp.apis.recipeapp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByCategory(String category);

}
