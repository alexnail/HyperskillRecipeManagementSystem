package recipes.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.exception.RecipeNotFoundException;
import recipes.model.IdModel;
import recipes.model.RecipeModel;
import recipes.service.RecipeService;

@RestController
@RequestMapping("/api/recipe")
@AllArgsConstructor
public class RecipeController {

    private RecipeService recipeService;

    @PostMapping("/new")
    public IdModel addRecipe(@Valid @RequestBody RecipeModel recipe) {
        return recipeService.addRecipe(recipe);
    }

    @GetMapping("/{id}")
    public RecipeModel getRecipe(@PathVariable("id") Long id) {
        return recipeService.getRecipe(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity recipeNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
