package recipes.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import recipes.model.RegisterModel;
import recipes.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
class RegisterControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository; // not the nicest way but we need to clean up repo before run

    @Test
    void registerNewUser() {
        userRepository.deleteAll();

        String url = "http://localhost:" + port + "/api/register";

        RegisterModel registerModel = RegisterModel.builder()
                .email("a@b.c").password("Password1!")
                .build();

        var response = restTemplate.postForEntity(url, registerModel, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void cantRegisterExistingUser() {
        userRepository.deleteAll();

        String url = "http://localhost:" + port + "/api/register";

        RegisterModel registerModel = RegisterModel.builder()
                .email("another@user.com").password("Password1!")
                .build();

        var response = restTemplate.postForEntity(url, registerModel, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        response = restTemplate.postForEntity(url, registerModel, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}