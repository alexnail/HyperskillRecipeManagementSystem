package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findByNameIgnoreCaseContainingOrderByDateDesc(String name);

    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
}
