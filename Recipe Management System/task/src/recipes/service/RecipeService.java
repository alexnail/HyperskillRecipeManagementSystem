package recipes.service;

import org.springframework.stereotype.Service;
import recipes.exception.RecipeNotFoundException;
import recipes.model.IdModel;
import recipes.model.RecipeModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RecipeService {

    private final Map<Long,RecipeModel> recipes = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public IdModel addRecipe(RecipeModel recipe) {
        var key = counter.incrementAndGet();
        recipes.put(key, recipe);
        return new IdModel(key);
    }

    public RecipeModel getRecipe(Long id) {
        if (recipes.get(id) == null) {
            throw new RecipeNotFoundException(id);
        }
        return recipes.get(id);
    }
}
