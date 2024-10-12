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
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public void saveRecipe(RecipeDTO recipe, User user) {

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
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public void delete(Recipe recipe) {
        /*
         * Recipe deletion is implemented
         */
    }

    @Override
    public Recipe update(RecipeDTO recipeDTO, Long recipeId) {

        /*
         * CHECK THAT THE USER TRYING TO UPDATE THE RECIPE IS THE SAME ONE WHO CREATED IT
         * IF YOU ARE AN ADMINISTRATOR YOU CAN UPDATE ANY RECIPE
         */

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
    }

    @Override
    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findByCategory(category);
    }

    @Override
    public List<Recipe> findBestRecipes() {
        var recipes = recipeRepository.findAll();
        return recipes.stream()
                .filter(recipe -> recipe.getRating() >= 4.8)
                .limit(6)
                .toList();
    }

}
