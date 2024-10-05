package recipes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.entity.User;
import recipes.repository.UserRepository;
import recipes.service.mapper.UserDetailsAdapter;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        var user = repository.findByUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            log.debug("User found: {}", user);
            return new UserDetailsAdapter(user);
        }
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
        var saved = repository.save(user);
        log.debug("User saved: {}", saved);
    }
}
