package com.tecsharp.apis.recipeapp.service.recipe.impl;

import com.tecsharp.apis.recipeapp.dto.RecipeDTO;
import com.tecsharp.apis.recipeapp.model.Recipe;
import com.tecsharp.apis.recipeapp.model.User;
import com.tecsharp.apis.recipeapp.repository.recipe.RecipeRepository;
import com.tecsharp.apis.recipeapp.service.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll() {
        try {
            return recipeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving recipes: " + e.getMessage(), e);
        }
    }

    @Override
    public Recipe findById(Long id) {
        try {
            return recipeRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error finding recipe with ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void saveRecipe(RecipeDTO recipe, User user) {
        try {
            var newRecipe = new Recipe();
            newRecipe.setTitle(recipe.getTitle());
            newRecipe.setDescription(recipe.getDescription());
            newRecipe.setPreparationTime(recipe.getPreparationTime());
            newRecipe.setDifficulty(recipe.getDifficulty());
            newRecipe.setCategory(recipe.getCategory());
            newRecipe.setRating(recipe.getRating());
            newRecipe.setCreatedBy(user);
            newRecipe.setDateCreation(new Date());
            newRecipe.setDateModification(new Date());
            recipeRepository.save(newRecipe);
        } catch (Exception e) {
            throw new RuntimeException("Error saving the recipe: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            recipeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting recipe with ID " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Recipe recipe) {
        try {
            recipeRepository.delete(recipe);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting recipe: " + e.getMessage(), e);
        }
    }

    @Override
    public Recipe update(RecipeDTO recipeDTO, Long recipeId) {
        try {
            var recipeToUpdate = recipeRepository.findById(recipeId).orElse(null);
            if (recipeToUpdate != null) {
                recipeToUpdate.setTitle(recipeDTO.getTitle());
                recipeToUpdate.setDescription(recipeDTO.getDescription());
                recipeToUpdate.setPreparationTime(recipeDTO.getPreparationTime());
                recipeToUpdate.setDifficulty(recipeDTO.getDifficulty());
                recipeToUpdate.setCategory(recipeDTO.getCategory());
                recipeToUpdate.setRating(recipeDTO.getRating());
                recipeToUpdate.setDateModification(new Date());
                recipeRepository.save(recipeToUpdate);
                recipeToUpdate.getCreatedBy().setPassword(null);
                return recipeToUpdate;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating recipe with ID " + recipeId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Recipe> findByCategory(String category) {
        try {
            return recipeRepository.findByCategory(category);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving recipes by category " + category + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<Recipe> findBestRecipes() {
        try {
            var recipes = recipeRepository.findAll();
            return recipes.stream()
                    .filter(recipe -> recipe.getRating() >= 4.8)
                    .limit(6)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving best recipes: " + e.getMessage(), e);
        }
    }
}
