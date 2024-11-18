package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.RecipeDTO;
import com.tecsharp.apis.recipeapp.model.Recipe;
import com.tecsharp.apis.recipeapp.repository.user.UserRepository;
import com.tecsharp.apis.recipeapp.service.recipe.RecipeService;
import com.tecsharp.apis.recipeapp.utils.AuthenticatedUserUtils;
import com.tecsharp.apis.recipeapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUserUtils authenticatedUserUtils;

    Logger log = LoggerFactory.getLogger(RecipeController.class);
    public static final String CONTROLLER_NAME = "RecipeController";

    @PostMapping("/create")
    public ResponseEntity<String> createRecipe(@RequestBody RecipeDTO recipe) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "createRecipe");
        recipeService.saveRecipe(recipe, authenticatedUserUtils.getAuthenticatedUser());
        log.info("Recipe {} created successfully", recipe.getTitle());
        return ResponseEntity.ok(Constants.RECIPE_CREATED_SUCCESS);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "updateRecipe");
        var recipe = recipeService.update(recipeDTO, id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> response = new HashMap<>();
        response.put("message", Constants.RECIPE_UPDATED_SUCCESS);
        response.put("recipe", recipe);
        log.info("Recipe {} updated successfully", recipe.getTitle());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "getAllRecipes");
        var recipes = recipeService.findAll();
        if (recipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        recipes.forEach(recipe -> {
            if (recipe.getCreatedBy() != null) {
                recipe.getCreatedBy().setPassword(null);
            }
        });
        log.info("Recipes found successfully");
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/best")
    public ResponseEntity<List<Recipe>> getBestRecipes() {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "getBestRecipes");
        var recipes = recipeService.findBestRecipes();
        if (recipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        recipes.forEach(recipe -> recipe.getCreatedBy().setPassword(null));
        log.info("Best recipes: {}", recipes);
        return ResponseEntity.ok(recipes);
    }
}
