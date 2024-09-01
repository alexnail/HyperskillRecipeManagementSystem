package recipes.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {
        super("Recipe with id=%d not found.".formatted(id));
    }
}
