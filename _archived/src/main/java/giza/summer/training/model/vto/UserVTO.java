package giza.summer.training.model.vto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVTO {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String mobile;
    private String gender;
    private Boolean isActive;
    private String userPassword;
    private Date createdOn;
}
