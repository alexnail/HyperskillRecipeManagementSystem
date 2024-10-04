package recipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import recipes.SecurityConfig;
import recipes.model.RecipeModel;
import recipes.service.RecipeService;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
@Import(SecurityConfig.class)
public class RecipeControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    @Test
    public void addRecipe_Unauthorized() throws Exception {
        var requestBuilder = post("/api/recipe/new");
        RecipeModel recipeModel = RecipeModel.builder()
                .build();
        requestBuilder.content(objectMapper.writeValueAsString(recipeModel));
        requestBuilder.contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void addRecipe_MockUser() throws Exception {
        var requestBuilder = post("/api/recipe/new");
        RecipeModel recipeModel = RecipeModel.builder()
                .date(LocalDateTime.now())
                .name("Recipe")
                .category("Category")
                .description("Description")
                .directions(List.of("Do something"))
                .ingredients(List.of("Secret ingredient"))
                .build();
        requestBuilder.content(objectMapper.writeValueAsString(recipeModel));
        requestBuilder.contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
}
