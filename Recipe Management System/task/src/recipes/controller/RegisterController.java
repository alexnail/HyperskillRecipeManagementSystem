package recipes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import recipes.model.RegisterModel;
import recipes.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @PostMapping
    public void register(@RequestBody @Valid RegisterModel registerModel) {
        log.debug("Registering user: {}", registerModel);
        userService.register(registerModel.getEmail(), registerModel.getPassword());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity bindException(BindException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
