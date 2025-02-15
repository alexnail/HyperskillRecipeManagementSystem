package recipes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import recipes.entity.Recipe;
import recipes.exception.NotAnAuthorException;
import recipes.exception.RecipeNotFoundException;
import recipes.model.IdModel;
import recipes.model.RecipeModel;
import recipes.repository.RecipeRepository;
import recipes.service.mapper.RecipeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public IdModel addRecipe(RecipeModel recipe) {
        log.debug("Adding recipe: {}", recipe);
        Recipe saved = recipeRepository.save(
                recipeMapper.toEntity(recipe,
                SecurityContextHolder.getContext().getAuthentication().getName()));
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
        if (!recipeRepository.findById(id).get().getAuthor()
                .equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new NotAnAuthorException();
        }
        recipeRepository.deleteById(id);
    }

    public void updateRecipe(Long id, RecipeModel recipe) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException(id);
        }
        var currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!recipeRepository.findById(id).get().getAuthor().equals(currentUser)) {
            throw new NotAnAuthorException();
        }
        var entity = recipeMapper.toEntity(recipe, currentUser);
        entity.setId(id);
        recipeRepository.save(entity);
    }

    public List<RecipeModel> search(String name, String category) {
        if ( (name == null && category == null ) || (name != null && category != null) ) {
            throw new IllegalArgumentException("One search parameter is required");
        }
        if (name != null) {
            return recipeRepository.findByNameIgnoreCaseContainingOrderByDateDesc(name)
                    .stream()
                    .map(recipeMapper::toModel)
                    .toList();
        }
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category)
                .stream()
                .map(recipeMapper::toModel)
                .toList();
    }
}
