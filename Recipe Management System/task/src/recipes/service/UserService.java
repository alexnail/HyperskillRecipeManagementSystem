package recipes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.repository.UserRepository;
import recipes.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserDetails loadUserByUsername(String email) {
        return null;
    }

    public void register(String email, String password) {
        if (repository.findByUsername(email) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        var user = User.builder()
                .username(email)
                .password(passwordEncoder.encode(password))
                .build();
        repository.save(user);
    }
}
