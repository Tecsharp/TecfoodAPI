package com.tecsharp.apis.recipeapp.service.recipe;

import com.tecsharp.apis.recipeapp.dto.RecipeDTO;
import com.tecsharp.apis.recipeapp.model.Recipe;
import com.tecsharp.apis.recipeapp.model.User;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();

    Recipe findById(Long id);

    void saveRecipe(RecipeDTO recipe, User user);

    void deleteById(Long id);

    void delete(Recipe recipe);

    Recipe update(RecipeDTO recipe, Long id);

    List<Recipe> findByCategory(String category);

    List<Recipe> findBestRecipes();
}
