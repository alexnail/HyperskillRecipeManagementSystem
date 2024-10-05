package recipes.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import recipes.model.IdModel;
import recipes.model.RecipeModel;
import recipes.model.RegisterModel;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"test"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecipeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeAll
    void beforeAll() {
        userRepository.deleteAll();
        recipeRepository.deleteAll();
    }

    @Test
    @Order(1)
    void newRecipe_woAuthentication() {
        String url = "http://localhost:" + port + "/api/recipe/new";

        RecipeModel recipeModel = RecipeModel.builder()
                .name("Fresh Mint Tea")
                .category("beverage")
                .description("Light, aromatic and refreshing beverage, ...")
                .ingredients(List.of("boiled water", "honey", "fresh mint leaves"))
                .directions(List.of("Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"))
                .build();

        var response = restTemplate.postForEntity(url, recipeModel, IdModel.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Order(2)
    void registerNewUser() {
        String url = "http://localhost:" + port + "/api/register";

        RegisterModel registerModel = RegisterModel.builder()
                .email("Cook_Programmer@somewhere.com").password("RecipeInBinary")
                .build();

        var response = restTemplate.postForEntity(url, registerModel, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(3)
    void newRecipe_wAuthentication() {
        String url = "http://localhost:" + port + "/api/recipe/new";

        RecipeModel recipeModel = RecipeModel.builder()
                .name("Mint Tea")
                .category("beverage")
                .description("Light, aromatic and refreshing beverage, ...")
                .ingredients(List.of("boiled water", "honey", "fresh mint leaves"))
                .directions(List.of("Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"))
                .build();

        restTemplate.withBasicAuth("Cook_Programmer@somewhere.com", "RecipeInBinary");

        var response = restTemplate.postForEntity(url, recipeModel, IdModel.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(1);
    }

    @Test
    @Order(4)
    void updateRecipe() {
        String url = "http://localhost:" + port + "/api/recipe/1";

        RecipeModel recipeModel = RecipeModel.builder()
                .name("Fresh Mint Tea")
                .category("beverage")
                .description("Light, aromatic and refreshing beverage, ...")
                .ingredients(List.of("boiled water", "honey", "fresh mint leaves"))
                .directions(List.of("Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"))
                .build();

        restTemplate.withBasicAuth("Cook_Programmer@somewhere.com", "RecipeInBinary");

        restTemplate.put(url, recipeModel);
    }

    @Test
    @Order(5)
    void registerAnotherUser() {
        String url = "http://localhost:" + port + "/api/register";

        RegisterModel registerModel = RegisterModel.builder()
                .email("CamelCaseRecipe@somewhere.com").password("C00k1es.")
                .build();

        var response = restTemplate.postForEntity(url, registerModel, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(6)
    void getRecipeAsAnotherUser() {
        String url = "http://localhost:" + port + "/api/recipe/1";

        restTemplate.withBasicAuth("CamelCaseRecipe@somewhere.com", "C00k1es.");
        var response = restTemplate.getForEntity(url, RecipeModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(7)
    void updateRecipeAsAnotherUser() {
        String url = "http://localhost:" + port + "/api/recipe/1";

        RecipeModel recipeModel = RecipeModel.builder()
                .name("Warming Ginger Tea")
                .category("beverage")
                .description("Ginger tea is a warming drink for cool weather, ...")
                .ingredients(List.of("1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"))
                .directions(List.of("Place all ingredients in a mug and fill with warm water (not too hot so you keep the beneficial honey compounds in tact)", "Steep for 5-10 minutes", "Drink and enjoy"))
                .build();

        restTemplate.withBasicAuth("CamelCaseRecipe@somewhere.com", "C00k1es.");

        restTemplate.put(url, recipeModel); // should be 403 Forbidden
    }
}