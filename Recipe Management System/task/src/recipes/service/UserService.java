package recipes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.entity.User;
import recipes.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserDetails loadUserByUsername(String email) {
        return null;
    }

    public void register(String email, String password) {
        var exists = repository.findByUsername(email);
        if (exists != null) {
            log.debug("User with email {} already exists.\n{}", email, exists);
            throw new IllegalArgumentException("User already exists");
        }
        var user = User.builder()
                .username(email)
                .password(passwordEncoder.encode(password))
                .build();
        repository.save(user);
    }
}
