package recipes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import recipes.entity.Recipe;
import recipes.exception.RecipeNotFoundException;
import recipes.model.IdModel;
import recipes.model.RecipeModel;
import recipes.repository.RecipeRepository;
import recipes.service.mapper.RecipeMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public IdModel addRecipe(RecipeModel recipe) {
        log.debug("Adding recipe: {}", recipe);
        Recipe saved = recipeRepository.save(recipeMapper.toEntity(recipe));
        return new IdModel(saved.getId());
    }

    public RecipeModel getRecipe(Long id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::toModel)
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException(id);
        }
        recipeRepository.deleteById(id);
    }
}
