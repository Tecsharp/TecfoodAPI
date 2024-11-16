package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.ErrorResponse;
import com.tecsharp.apis.recipeapp.dto.RecipeDTO;
import com.tecsharp.apis.recipeapp.model.Recipe;
import com.tecsharp.apis.recipeapp.repository.user.UserRepository;
import com.tecsharp.apis.recipeapp.service.recipe.RecipeService;
import com.tecsharp.apis.recipeapp.utils.AuthenticatedUserUtils;
import com.tecsharp.apis.recipeapp.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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

    @PostMapping("/create")
    public ResponseEntity<String> createRecipe(@RequestBody RecipeDTO recipe) {
        try {
            recipeService.saveRecipe(recipe, authenticatedUserUtils.getAuthenticatedUser());
            return ResponseEntity.ok(Constants.RECIPE_CREATED_SUCCESS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        /*
         * ADD ROLE DISTINCTION
         */

        Map<String, Object> response;
        try {
            var recipe = recipeService.update(recipeDTO, id);
            if (recipe == null) {
                return ResponseEntity.notFound().build();
            } else {
                response = new HashMap<>();
                response.put("message", Constants.RECIPE_UPDATED_SUCCESS);
                response.put("recipe", recipe);
            }
        } catch (Exception e) {
            var errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap(Constants.ERROR_MESSAGE, errorResponse));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRecipes() {
        try {
            var recipes = recipeService.findAll();
            if (recipes.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                recipes.forEach(recipe -> {
                    if (recipe.getCreatedBy() != null) {
                        recipe.getCreatedBy().setPassword(null);
                    }
                });
                return ResponseEntity.ok(recipes);
            }
        } catch (Exception e) {
            var errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/best")
    public ResponseEntity<Object> getBestRecipes() {
        try {
            var recipes = recipeService.findBestRecipes();
            if (recipes.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                recipes.forEach(recipe -> recipe.getCreatedBy().setPassword(null));
                return ResponseEntity.ok(recipes);
            }
        } catch (Exception e) {
            var errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
