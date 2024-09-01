package recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeModel {
    private String name;
    private String description;
    private String ingredients;
    private String directions;
}
