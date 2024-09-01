package recipes.service.mapper;

import org.springframework.stereotype.Component;
import recipes.entity.Recipe;
import recipes.model.RecipeModel;

@Component
public class RecipeMapper {
    public Recipe toEntity(RecipeModel recipe) {
        Recipe entity = new Recipe();
        entity.setName(recipe.getName());
        entity.setDescription(recipe.getDescription());
        entity.setIngredients(recipe.getIngredients());
        entity.setDirections(recipe.getDirections());
        return entity;
    }

    public RecipeModel toModel(Recipe recipe) {
        RecipeModel model = new RecipeModel();
        model.setName(recipe.getName());
        model.setDescription(recipe.getDescription());
        model.setIngredients(recipe.getIngredients());
        model.setDirections(recipe.getDirections());
        return model;
    }
}
