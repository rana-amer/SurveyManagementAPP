package giza.client.platform.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "FullName required")
    @Length(min = 5,max = 100,message = "fullName must be between 5 and 100 chars")
    private String fullName;
    @NotBlank(message = "Username required")
    @Length(min = 5,max = 50,message = "Username must be between 5 and 50 chars")
    private String username;
    @NotBlank(message = "Email required")
    @Email(message = "Email not valid")
    private String email;
    @NotBlank(message = "Password required")
    @Length(min=8,max=20,message = "Password should be between 8 - 20 characters")
    private String password;
    @Pattern(regexp = "male|female",message = "Invalid gender")
    private String gender;
    //    @NotBlank(message = "Active required")
//    @Pattern(regexp = "true|false",message = "Invalid Active")
    private Boolean isActive;
    private Date createdOn;
    @Pattern(regexp = "^\\d{11}$",message = "Invalid mobile number")
    private String mobile;
}
