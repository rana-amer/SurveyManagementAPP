package giza.summer.training.model.dto;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @NotBlank(message = "FullName required")
    @Length(min = 5,max = 100,message = "fullName must be between 5 and 100 chars")
    private String fullName;
    @NotBlank(message = "Username required")
    @Length(min = 5,max = 50,message = "fullName must be between 5 and 50 chars")
    private String username;
    @NotBlank(message = "Email required")
    @Email(message = "Email not valid")
    @Length(max = 100,message = "email too long")
    private String email;
//    @NotBlank(message = "Mobile required")
    @Pattern(regexp = "^\\d{11}$",message = "Invalid mobile number")
    private String mobile;
//    @NotBlank(message = "Gender required")
    @Pattern(regexp = "male|female",message = "Invalid gender")
    private String gender;
//    @NotBlank(message = "Active required")
//    @Pattern(regexp = "true|false",message = "Invalid Active")
    private Boolean isActive;
//    @Pattern(regexp = "^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&-+=()])(?=\\S+$).{8,20}$",message = "It contains 8 to 20 characters, at least one digit, at least one upper case alphabet, at least one lower case alphabet, at least one special character, doesn’t contain any white space.")
    @NotBlank(message = "Password required")
    @Length(min=8,max=20,message = "Password should be between 8 - 20 characters")
    private String userPassword;
}
