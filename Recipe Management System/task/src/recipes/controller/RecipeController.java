package recipes.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import recipes.model.RecipeModel;
import recipes.service.RecipeService;

@RestController
@RequestMapping("/api/recipe")
@AllArgsConstructor
public class RecipeController {

    private RecipeService recipeService;

    @PostMapping
    public void addRecipe(@RequestBody RecipeModel recipe) {
        recipeService.addRecipe(recipe);
    }

    @GetMapping
    public RecipeModel getRecipe() {
        return recipeService.getRecipe();
    }
}
