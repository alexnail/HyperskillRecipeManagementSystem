package recipes.service.mapper;

import org.springframework.stereotype.Component;
import recipes.entity.Recipe;
import recipes.model.RecipeModel;

@Component
public class RecipeMapper {
    public Recipe toEntity(RecipeModel recipe, String author) {
        Recipe entity = new Recipe();
        entity.setName(recipe.getName());
        entity.setDescription(recipe.getDescription());
        entity.setCategory(recipe.getCategory());
        //entity.setDate(recipe.getDate());
        entity.setIngredients(recipe.getIngredients());
        entity.setDirections(recipe.getDirections());
        entity.setAuthor(author);
        return entity;
    }

    public RecipeModel toModel(Recipe recipe) {
        RecipeModel model = new RecipeModel();
        model.setName(recipe.getName());
        model.setDescription(recipe.getDescription());
        model.setCategory(recipe.getCategory());
        model.setDate(recipe.getDate());
        model.setIngredients(recipe.getIngredients());
        model.setDirections(recipe.getDirections());
        return model;
    }
}
