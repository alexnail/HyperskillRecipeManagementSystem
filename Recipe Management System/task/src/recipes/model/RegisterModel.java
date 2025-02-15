package recipes.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterModel {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+\\..+)$",
            message = "Invalid email format")
    private String email;

    @NotBlank
    @Length(min = 8)
    private String password;
}
