package recipes.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.exception.NotAnAuthorException;
import recipes.exception.RecipeNotFoundException;
import recipes.model.IdModel;
import recipes.model.RecipeModel;
import recipes.service.RecipeService;

import java.util.List;

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

    @PutMapping("/{id}")
    public ResponseEntity updateRecipe(@PathVariable("id") Long id, @Valid @RequestBody RecipeModel recipe) {
        recipeService.updateRecipe(id, recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search/")
    public List<RecipeModel> searchRecipe(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "category", required = false) String category) {
        return recipeService.search(name, category);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity recipeNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAnAuthorException.class)
    public ResponseEntity notAnAuthorException() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
