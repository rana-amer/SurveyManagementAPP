package giza.client.platform.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "Username required")
    @Length(min = 5, max = 50, message = "Username must be between 5 and 50 chars")
    private String username;
    @NotBlank(message = "Password required")
    @Length(min = 8, max = 20, message = "Password should be between 8 - 20 characters")
    private String password;
}
