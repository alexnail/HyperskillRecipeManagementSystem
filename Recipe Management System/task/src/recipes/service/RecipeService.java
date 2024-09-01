package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.RecipeModel;

@Service
public class RecipeService {

    private RecipeModel recipe;

    public void addRecipe(RecipeModel recipe) {
        this.recipe = recipe;
    }

    public RecipeModel getRecipe() {
        return recipe;
    }
}
