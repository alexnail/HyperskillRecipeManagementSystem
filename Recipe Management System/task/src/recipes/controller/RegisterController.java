package recipes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.RegisterModel;
import recipes.service.UserService;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @PostMapping
    public void register(@RequestBody @Valid RegisterModel registerModel) {
        userService.register(registerModel.getEmail(), registerModel.getPassword());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
